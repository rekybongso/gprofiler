package me.rkyb.gprofiler.ui.main.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.*
import androidx.navigation.navGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import me.rkyb.gprofiler.R
import me.rkyb.gprofiler.ui.base.BaseFragment
import me.rkyb.gprofiler.databinding.FragmentSearchBinding
import me.rkyb.gprofiler.ui.adapter.UserSearchAdapter
import me.rkyb.gprofiler.ui.viewmodels.UserSearchViewModel
import me.rkyb.gprofiler.data.remote.response.ItemsResponse
import me.rkyb.gprofiler.utils.NetworkCheck
import me.rkyb.gprofiler.utils.Resource.Status.*
import me.rkyb.gprofiler.utils.extensions.ViewExt.doNavigate
import me.rkyb.gprofiler.utils.extensions.SearchExt.onError
import me.rkyb.gprofiler.utils.extensions.SearchExt.onSuccess
import me.rkyb.gprofiler.utils.extensions.SearchExt.onLoading

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(), UserSearchAdapter.Listener {

    /*  I'm using navGraphViewModels with defaultViewModelProviderFactory to keep
        fragment state across all navigation components. This method work with Hilt. */

    private val viewModel: UserSearchViewModel by navGraphViewModels(R.id.graph_nav) {
        defaultViewModelProviderFactory }

    private val networkCheck by lazy { NetworkCheck(requireContext()) }
    private val searchAdapter by lazy { UserSearchAdapter(this) }

    private lateinit var searchItem: MenuItem
    private lateinit var searchView: SearchView

    override var layoutId: Int = R.layout.fragment_search

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupObserver()
        setHasOptionsMenu(true)

    }

    override fun onItemClick(view: View, data: ItemsResponse) {
        val directions = data.userName?.let {
            SearchFragmentDirections.passResultToProfile(it)
        }

        //The doNavigate extension can be found on ViewExt.kt
        directions?.let { view.doNavigate(it) }
    }

    override fun onResume() {
        super.onResume()
        if (!networkCheck.isAvailable()){
            fBinding.onError(context?.getString(R.string.no_connection_notice))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.search_item_menu, menu)

        searchItem = menu.findItem(R.id.search_user)
        searchView = searchItem.actionView as SearchView

        searchView.run {
            queryHint = context.getString(R.string.query_hint)

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    clearFocus()
                    searchItem.collapseActionView()

                    if (networkCheck.isAvailable()) {
                        query?.let { viewModel.searchUsers(it) }
                    } else {
                        fBinding.onError(context.getString(R.string.no_connection_notice))
                    }

                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean = false
            })
        }
    }

    private fun setupObserver() {
        viewModel.dataFetched.observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status){
                    SUCCESS -> {
                        resource.data?.let { users ->
                            if (users.isNullOrEmpty()) {
                                fBinding.onError(null)
                            } else {
                                searchAdapter.renderList(users)
                                fBinding.onSuccess()
                            }
                        }
                    }
                    LOADING -> { fBinding.onLoading() }
                    ERROR -> { it.msg ?: resources.getString(R.string.error_notice) }
                }
            }
        })
    }

    private fun setupAdapter() {
        fBinding.rvUserList.adapter = searchAdapter
    }
}
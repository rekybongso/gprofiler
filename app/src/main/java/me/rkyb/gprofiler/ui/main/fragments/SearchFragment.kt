package me.rkyb.gprofiler.ui.main.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.*
import androidx.navigation.navGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import me.rkyb.gprofiler.R
import me.rkyb.gprofiler.ui.base.BaseFragment
import me.rkyb.gprofiler.databinding.FragmentSearchBinding
import me.rkyb.gprofiler.ui.adapter.MainRecyclerAdapter
import me.rkyb.gprofiler.ui.viewmodels.UserSearchViewModel
import me.rkyb.gprofiler.data.remote.response.ItemsResponse
import me.rkyb.gprofiler.utils.enum.ResourceStatus.*
import me.rkyb.gprofiler.utils.extensions.doNavigate
import me.rkyb.gprofiler.utils.extensions.onError
import me.rkyb.gprofiler.utils.extensions.onSuccess
import me.rkyb.gprofiler.utils.extensions.onLoading

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(), MainRecyclerAdapter.Listener {

    /*  I'm using navGraphViewModels with defaultViewModelProviderFactory to keep
        fragment state across all navigation components. This method work with Hilt. */

    private val searchViewModel: UserSearchViewModel by navGraphViewModels(R.id.graph_nav) {
        defaultViewModelProviderFactory }

    private val searchAdapter by lazy { MainRecyclerAdapter(this) }

    override var layoutId: Int = R.layout.fragment_search

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fBinding.rvUserList.adapter = searchAdapter

        setHasOptionsMenu(true)
        observeFetchedUser()

    }

    override fun onItemClick(view: View, data: ItemsResponse) {
        val directions = data.username?.let {
            SearchFragmentDirections.passResultToProfile(it)
        }

        directions?.let { view.doNavigate(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.search_item_menu, menu)

        val searchView: SearchView
        val searchItem = menu.findItem(R.id.search_user)

        searchView = searchItem.actionView as SearchView

        searchView.run {
            queryHint = context.getString(R.string.query_hint)

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    clearFocus()
                    searchItem.collapseActionView()

                    query?.let { searchViewModel.searchUsers(it) }

                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean = false
            })
        }
    }

    private fun observeFetchedUser() {
        searchViewModel.dataFetched.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                SUCCESS -> {
                    if (resource.data?.items.isNullOrEmpty()) {
                        fBinding.onError(null)
                    } else {
                        resource.data?.items?.let { users -> searchAdapter.renderList(users) }
                        fBinding.onSuccess()
                    }
                }
                LOADING -> fBinding.onLoading()
                ERROR -> fBinding.onError(resource.message)
            }
        })
    }
}
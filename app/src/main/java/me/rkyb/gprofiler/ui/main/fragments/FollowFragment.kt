package me.rkyb.gprofiler.ui.main.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import me.rkyb.gprofiler.R
import me.rkyb.gprofiler.data.remote.response.ItemsResponse
import me.rkyb.gprofiler.databinding.FragmentFollowBinding
import me.rkyb.gprofiler.ui.adapter.MainRecyclerAdapter
import me.rkyb.gprofiler.ui.base.BaseFragment
import me.rkyb.gprofiler.ui.viewmodels.FollowViewModel
import me.rkyb.gprofiler.utils.Constants.FRAGMENT_FOLLOW_TYPE
import me.rkyb.gprofiler.utils.Constants.FRAGMENT_USERNAME
import me.rkyb.gprofiler.utils.enum.FollowType.FOLLOWERS
import me.rkyb.gprofiler.utils.enum.FollowType.FOLLOWING
import me.rkyb.gprofiler.utils.enum.ResourceStatus.*
import me.rkyb.gprofiler.utils.extensions.doNavigate
import me.rkyb.gprofiler.utils.extensions.onError
import me.rkyb.gprofiler.utils.extensions.onLoading
import me.rkyb.gprofiler.utils.extensions.onSuccess

@AndroidEntryPoint
class FollowFragment : BaseFragment<FragmentFollowBinding>(), MainRecyclerAdapter.Listener {

    companion object {
        fun newInstance(userName: String, followType: String): FollowFragment {
            return FollowFragment().apply {
                arguments = Bundle().apply {

                    /* Bundle the username and follow type which needed to create
                       new instance of FollowFragment and share its data */

                    putString(FRAGMENT_USERNAME, userName)
                    putString(FRAGMENT_FOLLOW_TYPE, followType)

                }
            }
        }
    }

    private lateinit var username: String
    private var followType: String? = null

    private val followViewModel: FollowViewModel by viewModels()
    private val followAdapter by lazy { MainRecyclerAdapter(this) }

    override var layoutId: Int = R.layout.fragment_follow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Catch the data bundle and pass it into the variables
        arguments?.let {
            username = it.getString(FRAGMENT_USERNAME).toString()
            followType = it.getString(FRAGMENT_FOLLOW_TYPE)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fBinding.rvFollowList.adapter = followAdapter

        if (followType == resources.getString(R.string.followers_label)) {
            followViewModel.setFollowData(username, FOLLOWERS)
        } else {
            followViewModel.setFollowData(username, FOLLOWING)
        }

        observerFollowData()
    }

    override fun onItemClick(view: View, data: ItemsResponse) {

        //Get detail of user's following or follower

        val direction = data.username?.let {
            ProfileFragmentDirections.actionNavProfileSelf(it) }

        direction?.let { view.doNavigate(it) }

    }

    private fun observerFollowData(){
        followViewModel.getFollowData.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                SUCCESS -> {
                    if (resource.data.isNullOrEmpty()) {
                        fBinding.onError(null)
                    } else {
                        resource.data.let { user -> followAdapter.renderList(user) }
                        fBinding.onSuccess()
                    }
                }
                LOADING -> fBinding.onLoading()
                ERROR -> fBinding.onError(resource.message)
            }
        })
    }

}
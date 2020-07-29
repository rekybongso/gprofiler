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
import me.rkyb.gprofiler.ui.viewmodels.UserFollowViewModel
import me.rkyb.gprofiler.utils.enum.FollowType.*
import me.rkyb.gprofiler.utils.enum.ResourceStatus.*
import me.rkyb.gprofiler.utils.extensions.onError
import me.rkyb.gprofiler.utils.extensions.onLoading
import me.rkyb.gprofiler.utils.extensions.onSuccess
import me.rkyb.gprofiler.utils.Constants.FRAGMENT_FOLLOW_TYPE
import me.rkyb.gprofiler.utils.Constants.FRAGMENT_USERNAME

@AndroidEntryPoint
class FollowFragment : BaseFragment<FragmentFollowBinding>(), MainRecyclerAdapter.Listener {

    companion object {
        fun newInstance(userName: String, followType: String): FollowFragment {
            return FollowFragment().apply {
                arguments = Bundle().apply {
                    putString(FRAGMENT_USERNAME, userName)
                    putString(FRAGMENT_FOLLOW_TYPE, followType) }
            }
        }
    }

    private lateinit var username: String
    private var followType: String? = null

    private val followViewModel: UserFollowViewModel by viewModels()
    private val followAdapter by lazy { MainRecyclerAdapter(this) }

    override var layoutId: Int = R.layout.fragment_follow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            username = it.getString(FRAGMENT_USERNAME).toString()
            followType = it.getString(FRAGMENT_FOLLOW_TYPE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fBinding.rvFollowList.adapter = followAdapter

        when (followType) {
            resources.getString(R.string.followers_label) -> {
                followViewModel.setFollowData(username, FOLLOWERS)
            }
            resources.getString(R.string.following_label) -> {
                followViewModel.setFollowData(username, FOLLOWING)
            }
            else -> fBinding.onError(null)
        }

        observerFollowData()
    }

    override fun onItemClick(view: View, data: ItemsResponse) {
        //TODO("Not yet implemented")
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
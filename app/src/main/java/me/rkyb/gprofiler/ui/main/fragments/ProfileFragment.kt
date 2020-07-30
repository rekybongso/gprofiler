package me.rkyb.gprofiler.ui.main.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import me.rkyb.gprofiler.R
import me.rkyb.gprofiler.data.remote.response.UserProfileResponse
import me.rkyb.gprofiler.databinding.FragmentProfileBinding
import me.rkyb.gprofiler.ui.adapter.ProfilePagerAdapter
import me.rkyb.gprofiler.ui.base.BaseFragment
import me.rkyb.gprofiler.ui.viewmodels.ProfileViewModel
import me.rkyb.gprofiler.utils.Resource
import me.rkyb.gprofiler.utils.enum.ResourceStatus.SUCCESS

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    //navArgs used to pass data between fragment within the navigation components
    private val args by navArgs<ProfileFragmentArgs>()
    private val profileViewModel: ProfileViewModel by viewModels()

    private val observer = Observer<Resource<UserProfileResponse>> {
        if (it.status == SUCCESS) fBinding.userdata = it.data }

    override var layoutId: Int = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = fBinding.viewPager
        val tabs = fBinding.tabLayout

        //Mapping fragment based on given titles
        val tabTitles = arrayOf (
            resources.getString(R.string.followers_label),
            resources.getString(R.string.following_label))

        viewPager.adapter = ProfilePagerAdapter(this, args.username, tabTitles)
        TabLayoutMediator(tabs, viewPager){ tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        profileViewModel.apply {
            getUserData(args.username)
            usersFetched.observe(viewLifecycleOwner, observer)
        }
    }

}
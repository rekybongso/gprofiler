package me.rkyb.gprofiler.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import me.rkyb.gprofiler.ui.main.fragments.FollowFragment

class ProfilePagerAdapter (
    fragment: Fragment,
    private val userName: String,
    private val tabList: Array<String>): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = tabList.size

    override fun createFragment(position: Int): Fragment {
        return FollowFragment.newInstance(userName, tabList[position])
    }

}
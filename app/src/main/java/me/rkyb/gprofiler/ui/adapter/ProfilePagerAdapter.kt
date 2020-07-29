package me.rkyb.gprofiler.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import dagger.hilt.android.qualifiers.ApplicationContext
import me.rkyb.gprofiler.R
import me.rkyb.gprofiler.ui.main.fragments.FollowFragment

class ProfilePagerAdapter (
    fragment: Fragment,
    private val userName: String,
    @ApplicationContext private val context: Context): FragmentStateAdapter(fragment) {

    //Mapping fragment based on given titles
    private val tabTitles = listOf(
        context.getString(R.string.follower_label),
        context.getString(R.string.following_label)
    )

    override fun getItemCount(): Int = tabTitles.size

    override fun createFragment(position: Int): Fragment {
        return FollowFragment.newInstance(userName, tabTitles[position])
    }

}
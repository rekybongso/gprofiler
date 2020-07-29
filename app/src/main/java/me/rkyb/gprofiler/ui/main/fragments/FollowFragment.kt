package me.rkyb.gprofiler.ui.main.fragments

import android.os.Bundle
import me.rkyb.gprofiler.R
import me.rkyb.gprofiler.databinding.FragmentFollowBinding
import me.rkyb.gprofiler.ui.base.BaseFragment
import me.rkyb.gprofiler.utils.Constants

class FollowFragment : BaseFragment<FragmentFollowBinding>() {

    companion object {
        fun newInstance(fragmentType: String, userName: String): FollowFragment {
            return FollowFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.FRAGMENT_TYPE, fragmentType)
                    putString(Constants.FRAGMENT_USERNAME, userName)
                }
            }
        }
    }

    override var layoutId: Int = R.layout.fragment_follow

}
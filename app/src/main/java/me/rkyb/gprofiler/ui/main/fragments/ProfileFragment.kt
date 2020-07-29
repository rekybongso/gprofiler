package me.rkyb.gprofiler.ui.main.fragments

import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import me.rkyb.gprofiler.R
import me.rkyb.gprofiler.databinding.FragmentProfileBinding
import me.rkyb.gprofiler.ui.base.BaseFragment

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val args by navArgs<ProfileFragmentArgs>()

    override var layoutId: Int = R.layout.fragment_profile

}
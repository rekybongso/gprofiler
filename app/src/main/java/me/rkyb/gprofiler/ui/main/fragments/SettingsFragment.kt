package me.rkyb.gprofiler.ui.main.fragments

import dagger.hilt.android.AndroidEntryPoint
import me.rkyb.gprofiler.R
import me.rkyb.gprofiler.databinding.FragmentSettingsBinding
import me.rkyb.gprofiler.ui.base.BaseFragment

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    override var layoutId: Int = R.layout.fragment_settings

}
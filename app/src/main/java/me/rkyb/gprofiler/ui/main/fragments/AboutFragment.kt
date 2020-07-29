package me.rkyb.gprofiler.ui.main.fragments

import dagger.hilt.android.AndroidEntryPoint
import me.rkyb.gprofiler.R
import me.rkyb.gprofiler.databinding.FragmentAboutBinding
import me.rkyb.gprofiler.ui.base.BaseFragment

@AndroidEntryPoint
class AboutFragment : BaseFragment<FragmentAboutBinding>(){

    override var layoutId: Int = R.layout.fragment_about
    
}
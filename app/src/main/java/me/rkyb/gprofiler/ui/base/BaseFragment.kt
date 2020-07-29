package me.rkyb.gprofiler.ui.base

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment <VB: ViewDataBinding> : Fragment(){

    //Get layout id
    abstract var layoutId: Int

    //Init view binding
    lateinit var fBinding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)

        return fBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        //Clear menu if its not created by it respective fragment
        menu.clear()
    }
}
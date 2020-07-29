package me.rkyb.gprofiler.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import me.rkyb.gprofiler.R

abstract class BaseActivity <VB: ViewDataBinding> : AppCompatActivity() {

    // Get layout id
    abstract var layoutId: Int

    // Init view binding
    lateinit var aBinding: VB

    // Init navigation components --
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        aBinding = DataBindingUtil.setContentView(this, layoutId)

        /* In my case, using androidx fragment container view with
           findNavController doesn't work with this component.
           So the NavHostFragment created to supply the navController
           needed by the navigation component */

        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        setContentView(aBinding.root)
    }
}
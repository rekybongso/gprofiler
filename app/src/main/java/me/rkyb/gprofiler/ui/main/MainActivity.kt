package me.rkyb.gprofiler.ui.main

import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.navigation.ui.*
import dagger.hilt.android.AndroidEntryPoint
import me.rkyb.gprofiler.R
import me.rkyb.gprofiler.databinding.ActivityMainBinding
import me.rkyb.gprofiler.ui.base.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override var layoutId: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupUI()

    }

    private fun setupUI(){

        /*  The navController, navHostFragment, and appBarConfiguration
            has been late initiated on the base activity. */

        val toolbar = aBinding.mainContent.toolbar
        val drawerLayout = aBinding.drawerLayout
        val navigationView = aBinding.navigationView

        setSupportActionBar(toolbar)

        appBarConfiguration = AppBarConfiguration.Builder(
                              R.id.nav_search, R.id.nav_settings,
                              R.id.nav_about, R.id.nav_profile)
                              .setOpenableLayout(drawerLayout) //setDrawerLayout has been deprecated
                              .build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        navigationView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
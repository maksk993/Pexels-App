package com.maksk993.pexelsapp.presentation.screens.mainactivity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.maksk993.pexelsapp.R
import com.maksk993.pexelsapp.app.App
import com.maksk993.pexelsapp.databinding.ActivityMainBinding
import com.maksk993.pexelsapp.presentation.navigation.NavigationManager
import com.maksk993.pexelsapp.presentation.navigation.Screens


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (applicationContext as App).appComponent.inject(this)

        initBottomNavMenu()
        initNavigator()

        NavigationManager.setRootScreen(Screens.Home())
    }

    override fun onStart() {
        super.onStart()
        initObservers()
    }

    private fun initObservers() {
        NavigationManager.currentScreen.observe(this){
            if (it == Screens.Details().screenKey){
                binding.bottomNavView.visibility = View.GONE
            }
            else binding.bottomNavView.visibility = View.VISIBLE

            if (it == Screens.Home().screenKey)
                binding.bottomNavView.menu.findItem(R.id.nav_home).isChecked = true
        }
    }

    private fun initNavigator() {
        navigator = object : AppNavigator(this, binding.fragmentContainer.id) {
            override fun setupFragmentTransaction(
                screen: FragmentScreen,
                fragmentTransaction: FragmentTransaction,
                currentFragment: Fragment?,
                nextFragment: Fragment
            ) {
                super.setupFragmentTransaction(screen, fragmentTransaction, currentFragment, nextFragment)
                fragmentTransaction.setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
            }
        }
    }

    private fun initBottomNavMenu() {
        binding.bottomNavView.apply {
            itemIconTintList = null
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.nav_home -> {
                        NavigationManager.backToScreen(Screens.Home())
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.nav_bookmarks -> {
                        NavigationManager.navigateToScreen(Screens.Bookmarks())
                        return@setOnNavigationItemSelectedListener true
                    }
                }
                false
            }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        NavigationManager.setNavigator(navigator)
    }

    override fun onPause() {
        NavigationManager.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        if (binding.bottomNavView.visibility == View.VISIBLE)
            binding.bottomNavView.menu.findItem(R.id.nav_home).isChecked = true
        else binding.bottomNavView.visibility = View.VISIBLE
        super.onBackPressed()
    }

}
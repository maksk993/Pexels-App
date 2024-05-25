package com.maksk993.pexelsapp.presentation.screens

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.maksk993.pexelsapp.R
import com.maksk993.pexelsapp.databinding.ActivityMainBinding
import com.maksk993.pexelsapp.presentation.navigation.Screens


class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var navigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavMenu()
        initNavigator()

        if (savedInstanceState == null) viewModel.replaceScreen(Screens.Home())
    }

    override fun onStart() {
        super.onStart()
        viewModel.getFeaturedTitles()
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
                        viewModel.replaceScreen(Screens.Home())
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.nav_bookmarks -> {
                        viewModel.replaceScreen(Screens.Bookmarks())
                        return@setOnNavigationItemSelectedListener true
                    }
                }
                false
            }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        viewModel.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        viewModel.navigatorHolder.removeNavigator()
        super.onPause()
    }
}
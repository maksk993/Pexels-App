package com.maksk993.pexelsapp.presentation.screens

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.maksk993.pexelsapp.R
import com.maksk993.pexelsapp.databinding.ActivityMainBinding
import com.maksk993.pexelsapp.presentation.navigation.CiceroneInstance
import com.maksk993.pexelsapp.presentation.navigation.Screens

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private val router: Router = CiceroneInstance.router
    private val navigatorHolder = CiceroneInstance.navigatorHolder
    private lateinit var navigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavMenu()
        initNavigator()
    }

    private fun initNavigator() {
        navigator = AppNavigator(this, binding.fragmentContainer.id)
        router.replaceScreen(Screens.Home())
    }

    private fun initBottomNavMenu() {
        binding.bottomNavView.apply {
            itemIconTintList = null
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.nav_home -> {
                        router.replaceScreen(Screens.Home())
                        return@setOnNavigationItemSelectedListener true
                    }
                    R.id.nav_bookmarks -> {
                        router.replaceScreen(Screens.Bookmarks())
                        return@setOnNavigationItemSelectedListener true
                    }
                }
                false
            }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}
package com.maksk993.pexelsapp.presentation.screens

import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import com.maksk993.pexelsapp.presentation.navigation.CiceroneInstance

class MainViewModel : ViewModel() {
    private val router: Router = CiceroneInstance.router
    val navigatorHolder = CiceroneInstance.navigatorHolder

    fun replaceScreen(screen: Screen) = router.replaceScreen(screen)

}
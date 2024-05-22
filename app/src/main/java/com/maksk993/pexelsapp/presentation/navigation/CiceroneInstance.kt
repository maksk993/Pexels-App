package com.maksk993.pexelsapp.presentation.navigation

import com.github.terrakok.cicerone.Cicerone

object CiceroneInstance {
    private val cicerone = Cicerone.create()
    val router
        get() = cicerone.router
    val navigatorHolder
        get() = cicerone.getNavigatorHolder()
}
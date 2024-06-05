package com.maksk993.pexelsapp.presentation.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import com.maksk993.pexelsapp.domain.models.Photo

object NavigationManager {
    private val router: Router
    private val navigatorHolder: NavigatorHolder

    init {
        val cicerone = Cicerone.create()
        router = cicerone.router
        navigatorHolder = cicerone.getNavigatorHolder()
    }

    fun setNavigator(navigator: Navigator) = navigatorHolder.setNavigator(navigator)

    fun removeNavigator() = navigatorHolder.removeNavigator()

    private val _currentScreen: MutableLiveData<String> = MutableLiveData()
    val currentScreen: LiveData<String> = _currentScreen

    private val _focusedPhoto: MutableLiveData<Photo> = MutableLiveData()
    val focusedPhoto: LiveData<Photo> = _focusedPhoto

    fun setRootScreen(screen: Screen) {
        router.newRootScreen(screen)
        _currentScreen.value = screen.screenKey
    }

    fun navigateToScreen(screen: Screen, photo: Photo? = null) {
        photo?.let { _focusedPhoto.value = it }
        router.navigateTo(screen)
        _currentScreen.value = screen.screenKey
    }

    fun backToScreen(screen: Screen) {
        router.backTo(screen)
        _currentScreen.value = screen.screenKey
    }
}
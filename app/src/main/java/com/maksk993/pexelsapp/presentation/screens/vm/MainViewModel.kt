package com.maksk993.pexelsapp.presentation.screens.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.models.PhotoCallback
import com.maksk993.pexelsapp.domain.models.Collection
import com.maksk993.pexelsapp.domain.models.CollectionCallback
import com.maksk993.pexelsapp.domain.usecases.GetCuratedPhotos
import com.maksk993.pexelsapp.domain.usecases.GetFeaturedCollections
import com.maksk993.pexelsapp.presentation.navigation.CiceroneInstance
import kotlinx.coroutines.launch

class MainViewModel(
    private val getFeaturedCollections: GetFeaturedCollections,
    private val getPhotos: GetCuratedPhotos
) : ViewModel() {
    private val router: Router = CiceroneInstance.router
    val navigatorHolder = CiceroneInstance.navigatorHolder

    private val _currentFragment: MutableLiveData<String> = MutableLiveData()
    val currentFragment: LiveData<String> = _currentFragment

    fun replaceScreen(screen: Screen) {
        if (screen.screenKey == _currentFragment.value) return
        router.replaceScreen(screen)
        _currentFragment.value = screen.screenKey
    }

    fun navigateToScreen(screen: Screen) {
        if (screen.screenKey == _currentFragment.value) return
        router.navigateTo(screen)
        _currentFragment.value = screen.screenKey
    }

    fun backToScreen(screen: Screen) {
        if (screen.screenKey == _currentFragment.value) return
        router.backTo(screen)
        _currentFragment.value = screen.screenKey
    }

    private val _featuredCollections: MutableLiveData<MutableList<Collection>> = MutableLiveData(ArrayList())
    val featuredCollections: LiveData<MutableList<Collection>> = _featuredCollections

    private val _photos: MutableLiveData<MutableList<Photo>> = MutableLiveData(ArrayList())
    val photos: LiveData<MutableList<Photo>> = _photos

    fun getFeaturedCollections() {
        viewModelScope.launch {
            getFeaturedCollections.execute(object : CollectionCallback {
                override fun call(collections: List<Collection>) {
                    _featuredCollections.postValue(collections.toMutableList())
                }
            })
        }
    }

    fun getPhotos(collection: Collection = Collection("Popular")){
        viewModelScope.launch {
            getPhotos.execute(collection, object : PhotoCallback {
                override fun call(photos: List<Photo>?) {
                    _photos.postValue(photos?.toMutableList())
                }
            })
        }
    }

    private val _focusedPhoto: MutableLiveData<Photo> = MutableLiveData()
    val focusedPhoto: LiveData<Photo> = _focusedPhoto

    fun setFocusedPhoto(photo: Photo){
        _focusedPhoto.value = photo
    }
}
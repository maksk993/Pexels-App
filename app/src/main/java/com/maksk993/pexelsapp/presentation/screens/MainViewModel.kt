package com.maksk993.pexelsapp.presentation.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import com.maksk993.pexelsapp.data.repository.PhotoRepositoryImpl
import com.maksk993.pexelsapp.data.repository.CollectionRepositoryImpl
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.models.PhotoCallback
import com.maksk993.pexelsapp.domain.models.Collection
import com.maksk993.pexelsapp.domain.models.CollectionCallback
import com.maksk993.pexelsapp.domain.usecases.GetCuratedPhotos
import com.maksk993.pexelsapp.domain.usecases.GetFeaturedCollections
import com.maksk993.pexelsapp.presentation.navigation.CiceroneInstance
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val router: Router = CiceroneInstance.router
    val navigatorHolder = CiceroneInstance.navigatorHolder

    fun replaceScreen(screen: Screen) {
        router.replaceScreen(screen)
    }

    private val getFeaturedCollections = GetFeaturedCollections(CollectionRepositoryImpl())
    private val getPhotos = GetCuratedPhotos(PhotoRepositoryImpl())

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
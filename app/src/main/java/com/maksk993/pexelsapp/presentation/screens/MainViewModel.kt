package com.maksk993.pexelsapp.presentation.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import com.maksk993.pexelsapp.data.repository.PhotoRepositoryImpl
import com.maksk993.pexelsapp.data.repository.TitleRepositoryImpl
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.models.PhotoCallback
import com.maksk993.pexelsapp.domain.models.Title
import com.maksk993.pexelsapp.domain.models.TitleCallback
import com.maksk993.pexelsapp.domain.usecases.GetCuratedPhotos
import com.maksk993.pexelsapp.domain.usecases.GetFeaturedTitles
import com.maksk993.pexelsapp.presentation.navigation.CiceroneInstance
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val router: Router = CiceroneInstance.router
    val navigatorHolder = CiceroneInstance.navigatorHolder

    fun replaceScreen(screen: Screen) {
        router.replaceScreen(screen)
    }

    private val getFeaturedTitles = GetFeaturedTitles(TitleRepositoryImpl())
    private val getPhotos = GetCuratedPhotos(PhotoRepositoryImpl())

    private val _featuredTitles: MutableLiveData<MutableList<Title>> = MutableLiveData(ArrayList())
    val featuredTitles: LiveData<MutableList<Title>> = _featuredTitles

    private val _photos: MutableLiveData<MutableList<Photo>> = MutableLiveData(ArrayList())
    val photos: LiveData<MutableList<Photo>> = _photos

    fun getFeaturedTitles() {
        viewModelScope.launch {
            getFeaturedTitles.execute(object : TitleCallback {
                override fun call(title: Title) {
                    val list = _featuredTitles.value ?: ArrayList()
                    list.add(title)
                    _featuredTitles.postValue(list)
                }
            })
        }
    }

    fun getPhotos(title: Title){
        viewModelScope.launch {
            getPhotos.execute(title, object : PhotoCallback {
                override fun call(photo: Photo) {
                    val list = _photos.value ?: ArrayList()
                    list.add(photo)
                    _photos.postValue(list)
                }
            })
        }
    }
}
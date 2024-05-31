package com.maksk993.pexelsapp.presentation.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maksk993.pexelsapp.domain.models.Collection
import com.maksk993.pexelsapp.domain.models.CollectionCallback
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.models.PhotoCallback
import com.maksk993.pexelsapp.domain.usecases.GetCuratedPhotos
import com.maksk993.pexelsapp.domain.usecases.GetFeaturedCollections
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getFeaturedCollections: GetFeaturedCollections,
    private val getCuratedPhotos: GetCuratedPhotos,
) : ViewModel() {

    init {
        getFeaturedCollections()
        getPhotos()
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
            getCuratedPhotos.execute(collection, object : PhotoCallback {
                override fun call(photos: List<Photo>?) {
                    _photos.postValue(photos?.toMutableList())
                }
            })
        }
    }
}
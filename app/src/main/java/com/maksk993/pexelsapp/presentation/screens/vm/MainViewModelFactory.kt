package com.maksk993.pexelsapp.presentation.screens.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maksk993.pexelsapp.domain.usecases.GetCuratedPhotos
import com.maksk993.pexelsapp.domain.usecases.GetFeaturedCollections

class MainViewModelFactory(
    private val getFeaturedCollections: GetFeaturedCollections,
    private val getPhotos: GetCuratedPhotos
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            getPhotos = getPhotos,
            getFeaturedCollections = getFeaturedCollections
        ) as T
    }
}
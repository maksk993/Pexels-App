package com.maksk993.pexelsapp.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maksk993.pexelsapp.domain.usecases.GetCuratedPhotos
import com.maksk993.pexelsapp.domain.usecases.GetFeaturedCollections

class HomeViewModelFactory(
    private val getFeaturedCollections: GetFeaturedCollections,
    private val getCuratedPhotos: GetCuratedPhotos,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(
            getFeaturedCollections = getFeaturedCollections,
            getCuratedPhotos = getCuratedPhotos
        ) as T
    }
}
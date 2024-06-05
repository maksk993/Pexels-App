package com.maksk993.pexelsapp.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maksk993.pexelsapp.domain.usecases.GetCuratedPhotosUseCase
import com.maksk993.pexelsapp.domain.usecases.GetFeaturedCollectionsUseCase
import javax.inject.Inject

class HomeViewModelFactory @Inject constructor(
    val getFeaturedCollectionsUseCase: GetFeaturedCollectionsUseCase,
    val getCuratedPhotosUseCase: GetCuratedPhotosUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(
            getFeaturedCollectionsUseCase = getFeaturedCollectionsUseCase,
            getCuratedPhotosUseCase = getCuratedPhotosUseCase
        ) as T
    }
}
package com.maksk993.pexelsapp.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maksk993.pexelsapp.domain.usecases.AddPhotoToBookmarks
import com.maksk993.pexelsapp.domain.usecases.GetCuratedPhotos
import com.maksk993.pexelsapp.domain.usecases.GetFeaturedCollections
import com.maksk993.pexelsapp.domain.usecases.GetPhotosFromBookmarks

class MainViewModelFactory(
    private val getFeaturedCollections: GetFeaturedCollections,
    private val getCuratedPhotos: GetCuratedPhotos,
    private val addPhotoToBookmarks: AddPhotoToBookmarks,
    private val getPhotosFromBookmarks: GetPhotosFromBookmarks
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            getCuratedPhotos = getCuratedPhotos,
            getFeaturedCollections = getFeaturedCollections,
            addPhotoToBookmarks = addPhotoToBookmarks,
            getPhotosFromBookmarks = getPhotosFromBookmarks
        ) as T
    }
}
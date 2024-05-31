package com.maksk993.pexelsapp.presentation.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maksk993.pexelsapp.domain.usecases.AddPhotoToBookmarks
import com.maksk993.pexelsapp.domain.usecases.DeletePhotoFromBookmarks
import com.maksk993.pexelsapp.domain.usecases.WasPhotoAddedToBookmarks

class DetailsViewModelFactory(
    private val addPhotoToBookmarks: AddPhotoToBookmarks,
    private val wasPhotoAddedToBookmarks: WasPhotoAddedToBookmarks,
    private val deletePhotoFromBookmarks: DeletePhotoFromBookmarks
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailsViewModel(
            addPhotoToBookmarks = addPhotoToBookmarks,
            wasPhotoAddedToBookmarks = wasPhotoAddedToBookmarks,
            deletePhotoFromBookmarks = deletePhotoFromBookmarks
        ) as T
    }
}
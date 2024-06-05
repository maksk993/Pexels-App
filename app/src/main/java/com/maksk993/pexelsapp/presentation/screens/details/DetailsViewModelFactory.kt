package com.maksk993.pexelsapp.presentation.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maksk993.pexelsapp.domain.usecases.AddPhotoToBookmarksUseCase
import com.maksk993.pexelsapp.domain.usecases.DeletePhotoFromBookmarksUseCase
import com.maksk993.pexelsapp.domain.usecases.GetFileSizeOfPhotoUseCase
import com.maksk993.pexelsapp.domain.usecases.WasPhotoAddedToBookmarksUseCase
import javax.inject.Inject

class DetailsViewModelFactory @Inject constructor(
    val addPhotoToBookmarksUseCase: AddPhotoToBookmarksUseCase,
    val wasPhotoAddedToBookmarksUseCase: WasPhotoAddedToBookmarksUseCase,
    val deletePhotoFromBookmarksUseCase: DeletePhotoFromBookmarksUseCase,
    val getFileSizeOfPhotoUseCase: GetFileSizeOfPhotoUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailsViewModel(
            addPhotoToBookmarksUseCase = addPhotoToBookmarksUseCase,
            wasPhotoAddedToBookmarksUseCase = wasPhotoAddedToBookmarksUseCase,
            deletePhotoFromBookmarksUseCase = deletePhotoFromBookmarksUseCase,
            getFileSizeOfPhotoUseCase = getFileSizeOfPhotoUseCase
        ) as T
    }
}
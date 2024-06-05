package com.maksk993.pexelsapp.presentation.screens.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maksk993.pexelsapp.domain.usecases.GetPhotosFromBookmarksUseCase
import javax.inject.Inject

class BookmarksViewModelFactory @Inject constructor(
    val getPhotosFromBookmarksUseCase: GetPhotosFromBookmarksUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookmarksViewModel(getPhotosFromBookmarksUseCase = getPhotosFromBookmarksUseCase) as T
    }
}
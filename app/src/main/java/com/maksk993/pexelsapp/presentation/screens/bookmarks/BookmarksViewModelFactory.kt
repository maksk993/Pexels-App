package com.maksk993.pexelsapp.presentation.screens.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maksk993.pexelsapp.domain.usecases.GetPhotosFromBookmarks

class BookmarksViewModelFactory(
    private val getPhotosFromBookmarks: GetPhotosFromBookmarks
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookmarksViewModel(getPhotosFromBookmarks = getPhotosFromBookmarks) as T
    }
}
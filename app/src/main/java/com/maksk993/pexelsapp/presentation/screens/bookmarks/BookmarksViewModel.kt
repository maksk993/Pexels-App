package com.maksk993.pexelsapp.presentation.screens.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.usecases.GetPhotosFromBookmarks
import kotlinx.coroutines.launch

class BookmarksViewModel(
    private val getPhotosFromBookmarks: GetPhotosFromBookmarks
) : ViewModel() {
    private val _bookmarks: MutableLiveData<MutableList<Photo?>> = MutableLiveData(ArrayList())
    val bookmarks: LiveData<MutableList<Photo?>> = _bookmarks

    fun getPhotosFromBookmarks(){
        viewModelScope.launch {
            _bookmarks.postValue(getPhotosFromBookmarks.execute().toMutableList())
        }
    }
}
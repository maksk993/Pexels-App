package com.maksk993.pexelsapp.presentation.screens.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.usecases.AddPhotoToBookmarks
import com.maksk993.pexelsapp.domain.usecases.WasPhotoAddedToBookmarks
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val addPhotoToBookmarks: AddPhotoToBookmarks,
    private val wasPhotoAddedToBookmarks: WasPhotoAddedToBookmarks
) : ViewModel() {
    fun addPhotoToBookmarks(photo: Photo){
        viewModelScope.launch {
            addPhotoToBookmarks.execute(photo)
        }
    }

    private val _wasAdded: MutableLiveData<Boolean> = MutableLiveData(false)
    val wasAdded: LiveData<Boolean> = _wasAdded

    fun wasPhotoAddedToBookmarks(photo: Photo) {
        viewModelScope.launch {
            _wasAdded.value = wasPhotoAddedToBookmarks.execute(photo)
        }
    }
}
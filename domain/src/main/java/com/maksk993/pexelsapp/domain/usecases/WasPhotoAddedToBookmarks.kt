package com.maksk993.pexelsapp.domain.usecases

import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.repository.BookmarksRepository

class WasPhotoAddedToBookmarks(private val repository: BookmarksRepository) {
    suspend fun execute(photo: Photo): Boolean = repository.wasPhotoAdded(photo)
}
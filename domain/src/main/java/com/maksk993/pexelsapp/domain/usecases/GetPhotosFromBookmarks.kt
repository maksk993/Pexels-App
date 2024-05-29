package com.maksk993.pexelsapp.domain.usecases

import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.repository.BookmarksRepository

class GetPhotosFromBookmarks(private val repository: BookmarksRepository) {
    suspend fun execute(): List<Photo?> = repository.getPhotos()
}
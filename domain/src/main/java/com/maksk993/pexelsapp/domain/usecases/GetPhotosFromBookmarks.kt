package com.maksk993.pexelsapp.domain.usecases

import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.repository.BookmarksRepository
import javax.inject.Inject

class GetPhotosFromBookmarks @Inject constructor(val repository: BookmarksRepository) {
    suspend fun execute(): List<Photo?> = repository.getPhotos()
}
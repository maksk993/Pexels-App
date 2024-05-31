package com.maksk993.pexelsapp.domain.usecases

import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.repository.BookmarksRepository
import javax.inject.Inject

class DeletePhotoFromBookmarks @Inject constructor(val repository: BookmarksRepository) {
    suspend fun execute(photo: Photo) {
        repository.deletePhoto(photo)
    }
}
package com.maksk993.pexelsapp.domain.usecases

import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.repository.BookmarksRepository
import io.reactivex.Single
import javax.inject.Inject

class WasPhotoAddedToBookmarksUseCase @Inject constructor(val repository: BookmarksRepository) {
    fun execute(photo: Photo): Single<Boolean> = repository.wasPhotoAdded(photo)
}
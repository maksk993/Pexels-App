package com.maksk993.pexelsapp.domain.usecases

import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.repository.BookmarksRepository
import io.reactivex.Completable
import javax.inject.Inject

class AddPhotoToBookmarksUseCase @Inject constructor(val repository: BookmarksRepository) {
    fun execute(photo: Photo): Completable = repository.addPhoto(photo)
}
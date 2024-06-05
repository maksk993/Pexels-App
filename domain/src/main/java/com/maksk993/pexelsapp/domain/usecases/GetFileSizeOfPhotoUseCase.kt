package com.maksk993.pexelsapp.domain.usecases

import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.repository.PhotoRepository
import io.reactivex.Single
import javax.inject.Inject

class GetFileSizeOfPhotoUseCase @Inject constructor(val repository: PhotoRepository) {
    fun execute(photo: Photo): Single<Long> = repository.getFileSize(photo = photo)
}
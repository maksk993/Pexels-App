package com.maksk993.pexelsapp.domain.usecases

import com.maksk993.pexelsapp.domain.models.Collection
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.repository.PhotoRepository
import io.reactivex.Maybe
import javax.inject.Inject

class GetCuratedPhotos @Inject constructor(val repository: PhotoRepository) {
    fun execute(title: Collection): Maybe<List<Photo>> = repository.getPhoto(title)
}
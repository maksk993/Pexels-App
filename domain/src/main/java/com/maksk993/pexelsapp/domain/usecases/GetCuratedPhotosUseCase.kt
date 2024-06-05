package com.maksk993.pexelsapp.domain.usecases

import com.maksk993.pexelsapp.domain.models.FeaturedCollection
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.repository.PhotoRepository
import io.reactivex.Maybe
import javax.inject.Inject

class GetCuratedPhotosUseCase @Inject constructor(val repository: PhotoRepository) {
    fun execute(title: FeaturedCollection): Maybe<List<Photo>> = repository.getPhotos(title)
}
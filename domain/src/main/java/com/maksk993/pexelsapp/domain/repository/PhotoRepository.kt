package com.maksk993.pexelsapp.domain.repository

import com.maksk993.pexelsapp.domain.models.FeaturedCollection
import com.maksk993.pexelsapp.domain.models.Photo
import io.reactivex.Maybe
import io.reactivex.Single

interface PhotoRepository {
    fun getPhotos(title: FeaturedCollection): Maybe<List<Photo>>
    fun getFileSize(photo: Photo): Single<Long>
}
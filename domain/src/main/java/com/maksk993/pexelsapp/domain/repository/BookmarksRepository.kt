package com.maksk993.pexelsapp.domain.repository

import com.maksk993.pexelsapp.domain.models.Photo
import io.reactivex.Completable
import io.reactivex.Single

interface BookmarksRepository {
    fun addPhoto(photo: Photo): Completable
    fun deletePhoto(photo: Photo): Completable
    fun getPhotos(): Single<List<Photo?>>
    fun wasPhotoAdded(photo: Photo): Single<Boolean>
}
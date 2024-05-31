package com.maksk993.pexelsapp.domain.repository

import com.maksk993.pexelsapp.domain.models.Photo

interface BookmarksRepository {
    suspend fun addPhoto(photo: Photo)
    suspend fun getPhotos(): List<Photo?>
    suspend fun wasPhotoAdded(photo: Photo): Boolean
}
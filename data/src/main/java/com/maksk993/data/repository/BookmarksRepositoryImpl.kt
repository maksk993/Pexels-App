package com.maksk993.data.repository

import com.maksk993.data.models.room.BookmarksDao
import com.maksk993.data.models.room.PhotoDbEntity
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.repository.BookmarksRepository

class BookmarksRepositoryImpl(private val dao: BookmarksDao) : BookmarksRepository {

    override suspend fun addPhoto(photo: Photo) = dao.addPhoto(PhotoDbEntity.toDbEntity(photo))

    override suspend fun getPhotos(): List<Photo?> = dao.getPhotos().map { it?.toPhoto() }
    override suspend fun wasPhotoAdded(photo: Photo): Boolean = dao.doesPhotoExists(photo.id)
}
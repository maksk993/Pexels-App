package com.maksk993.data.repository

import com.maksk993.data.models.room.BookmarksDao
import com.maksk993.data.models.room.PhotoDbEntity
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.repository.BookmarksRepository
import io.reactivex.Completable
import io.reactivex.Single

class BookmarksRepositoryImpl(private val dao: BookmarksDao) : BookmarksRepository {

    override fun addPhoto(photo: Photo): Completable {
        return Completable.fromAction {
            dao.addPhoto(PhotoDbEntity.toDbEntity(photo))
        }
    }
    override fun deletePhoto(photo: Photo): Completable {
        return Completable.fromAction {
            dao.deletePhoto(photo.id)
        }
    }

    override fun getPhotos(): Single<List<Photo?>> {
        return dao.getPhotos().map { list ->
            list.map { it?.toPhoto() }
        }
    }
    override fun wasPhotoAdded(photo: Photo): Single<Boolean> = dao.doesPhotoExists(photo.id)
}
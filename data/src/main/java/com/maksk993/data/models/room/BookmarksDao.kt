package com.maksk993.data.models.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface BookmarksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhoto(photoDbEntity: PhotoDbEntity): Completable

    @Query("DELETE FROM bookmarks WHERE id = :id")
    fun deletePhoto(id: Long): Completable

    @Query("SELECT * FROM bookmarks")
    fun getPhotos(): Single<List<PhotoDbEntity?>>

    @Query("SELECT EXISTS (SELECT 1 FROM bookmarks WHERE id = :id)")
    fun doesPhotoExists(id: Long): Single<Boolean>
}
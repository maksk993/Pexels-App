package com.maksk993.data.models.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookmarksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhoto(photoDbEntity: PhotoDbEntity)

    @Query("SELECT * FROM bookmarks")
    suspend fun getPhotos(): List<PhotoDbEntity?>

    @Query("SELECT EXISTS (SELECT 1 FROM bookmarks WHERE id = :id)")
    suspend fun doesPhotoExists(id: Long): Boolean
}
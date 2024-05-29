package com.maksk993.data.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maksk993.pexelsapp.domain.models.Photo

@Entity(
    tableName = "bookmarks"
)
data class PhotoDbEntity(
    @PrimaryKey(autoGenerate = false) val id: Long,
    val url: String,
    val photographer: String
) {
    companion object {
        fun toDbEntity(photo: Photo): PhotoDbEntity {
            return PhotoDbEntity(
                id = photo.id,
                photographer = photo.photographer,
                url = photo.src.original
            )
        }
    }

    fun toPhoto(): Photo {
        return Photo(
            id = id,
            photographer = photographer,
            src = Photo.Src(url)
        )
    }
}
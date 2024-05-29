package com.maksk993.data.models.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [PhotoDbEntity::class]
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getDao(): BookmarksDao
}
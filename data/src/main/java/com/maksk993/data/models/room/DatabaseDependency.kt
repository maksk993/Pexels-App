package com.maksk993.data.models.room

import android.content.Context
import androidx.room.Room

class DatabaseDependency(val context: Context) {
    private val dataBase: AppDataBase by lazy {
        Room.databaseBuilder(context, AppDataBase::class.java, "database.db")
            .build()
    }

    fun getDao(): BookmarksDao = dataBase.getDao()
}
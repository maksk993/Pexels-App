package com.maksk993.data.models.room

import android.content.Context
import androidx.room.Room
import javax.inject.Inject

class DatabaseDependency @Inject constructor(val context: Context) {
    private val dataBase: AppDataBase by lazy {
        Room.databaseBuilder(context, AppDataBase::class.java, "database.db")
            .build()
    }

    fun getDao(): BookmarksDao = dataBase.getDao()
}
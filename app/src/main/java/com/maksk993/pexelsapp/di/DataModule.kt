package com.maksk993.pexelsapp.di

import android.content.Context
import com.maksk993.data.models.room.DatabaseDependency
import com.maksk993.data.repository.BookmarksRepositoryImpl
import com.maksk993.data.repository.CollectionRepositoryImpl
import com.maksk993.data.repository.PhotoRepositoryImpl
import com.maksk993.pexelsapp.domain.repository.BookmarksRepository
import com.maksk993.pexelsapp.domain.repository.CollectionRepository
import com.maksk993.pexelsapp.domain.repository.PhotoRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    @Provides
    fun provideDatabase(context: Context): DatabaseDependency = DatabaseDependency(context)

    @Provides
    fun provideBookmarksRepository(database: DatabaseDependency): BookmarksRepository {
        return BookmarksRepositoryImpl(database.getDao())
    }

    @Provides
    fun provideCollectionRepository(): CollectionRepository = CollectionRepositoryImpl()

    @Provides
    fun providePhotoRepository(): PhotoRepository = PhotoRepositoryImpl()
}
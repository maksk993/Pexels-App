package com.maksk993.pexelsapp.di

import com.maksk993.pexelsapp.data.repository.CollectionRepositoryImpl
import com.maksk993.pexelsapp.data.repository.PhotoRepositoryImpl
import com.maksk993.pexelsapp.domain.repository.CollectionRepository
import com.maksk993.pexelsapp.domain.repository.PhotoRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    @Provides
    fun provideCollectionRepository(): CollectionRepository = CollectionRepositoryImpl()

    @Provides
    fun providePhotoRepository(): PhotoRepository = PhotoRepositoryImpl()
}
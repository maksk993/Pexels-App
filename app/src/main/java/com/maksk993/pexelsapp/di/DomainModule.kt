package com.maksk993.pexelsapp.di

import com.maksk993.pexelsapp.domain.repository.CollectionRepository
import com.maksk993.pexelsapp.domain.repository.PhotoRepository
import com.maksk993.pexelsapp.domain.usecases.GetCuratedPhotos
import com.maksk993.pexelsapp.domain.usecases.GetFeaturedCollections
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    fun provideGetCuratedPhotos(repository: PhotoRepository): GetCuratedPhotos {
        return GetCuratedPhotos(repository)
    }

    @Provides
    fun provideGetFeaturedCollections(repository: CollectionRepository): GetFeaturedCollections {
        return GetFeaturedCollections(repository)
    }
}
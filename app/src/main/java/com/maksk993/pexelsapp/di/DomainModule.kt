package com.maksk993.pexelsapp.di

import com.maksk993.pexelsapp.domain.repository.BookmarksRepository
import com.maksk993.pexelsapp.domain.repository.CollectionRepository
import com.maksk993.pexelsapp.domain.repository.PhotoRepository
import com.maksk993.pexelsapp.domain.usecases.AddPhotoToBookmarks
import com.maksk993.pexelsapp.domain.usecases.GetCuratedPhotos
import com.maksk993.pexelsapp.domain.usecases.GetFeaturedCollections
import com.maksk993.pexelsapp.domain.usecases.GetPhotosFromBookmarks
import com.maksk993.pexelsapp.domain.usecases.WasPhotoAddedToBookmarks
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

    @Provides
    fun provideAddPhotoToBookmarks(repository: BookmarksRepository): AddPhotoToBookmarks {
        return AddPhotoToBookmarks(repository)
    }

    @Provides
    fun provideGetPhotosFromBookmarks(repository: BookmarksRepository): GetPhotosFromBookmarks {
        return GetPhotosFromBookmarks(repository)
    }

    @Provides
    fun provideWasPhotoAddedToBookmarks(repository: BookmarksRepository): WasPhotoAddedToBookmarks {
        return WasPhotoAddedToBookmarks(repository)
    }
}
package com.maksk993.pexelsapp.di

import android.content.Context
import com.maksk993.pexelsapp.domain.models.Collection
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.usecases.AddPhotoToBookmarks
import com.maksk993.pexelsapp.domain.usecases.GetCuratedPhotos
import com.maksk993.pexelsapp.domain.usecases.GetFeaturedCollections
import com.maksk993.pexelsapp.domain.usecases.GetPhotosFromBookmarks
import com.maksk993.pexelsapp.domain.usecases.WasPhotoAddedToBookmarks
import com.maksk993.pexelsapp.presentation.screens.bookmarks.BookmarksViewModelFactory
import com.maksk993.pexelsapp.presentation.screens.details.DetailsViewModelFactory
import com.maksk993.pexelsapp.presentation.screens.home.HomeViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {
    @Provides
    fun provideContext(): Context = context

    @Provides
    fun provideHomeViewModelFactory(
        getCuratedPhotos: GetCuratedPhotos,
        getFeaturedCollections: GetFeaturedCollections,
    ) : HomeViewModelFactory {
        return HomeViewModelFactory(
            getCuratedPhotos = getCuratedPhotos,
            getFeaturedCollections = getFeaturedCollections,
        )
    }

    @Provides
    fun provideBookmarksViewModelFactory(
        getPhotosFromBookmarks: GetPhotosFromBookmarks
    ) : BookmarksViewModelFactory {
        return BookmarksViewModelFactory(
            getPhotosFromBookmarks = getPhotosFromBookmarks
        )
    }

    @Provides
    fun provideDetailsViewModelFactory(
        addPhotoToBookmarks: AddPhotoToBookmarks,
        wasPhotoAddedToBookmarks: WasPhotoAddedToBookmarks
    ) : DetailsViewModelFactory {
        return DetailsViewModelFactory(
            addPhotoToBookmarks = addPhotoToBookmarks,
            wasPhotoAddedToBookmarks = wasPhotoAddedToBookmarks
        )
    }

    @Provides
    fun provideFeaturedItems(): MutableList<Collection> = ArrayList()

    @Provides
    fun providePhotosItems(): MutableList<Photo> = ArrayList()
}
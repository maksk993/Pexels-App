package com.maksk993.pexelsapp.di

import android.content.Context
import com.maksk993.pexelsapp.domain.models.Collection
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.usecases.AddPhotoToBookmarks
import com.maksk993.pexelsapp.domain.usecases.GetCuratedPhotos
import com.maksk993.pexelsapp.domain.usecases.GetFeaturedCollections
import com.maksk993.pexelsapp.domain.usecases.GetPhotosFromBookmarks
import com.maksk993.pexelsapp.presentation.vm.MainViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {
    @Provides
    fun provideContext(): Context = context

    @Provides
    fun provideViewModelFactory(
        getCuratedPhotos: GetCuratedPhotos,
        getFeaturedCollections: GetFeaturedCollections,
        addPhotoToBookmarks: AddPhotoToBookmarks,
        getPhotosFromBookmarks: GetPhotosFromBookmarks
    ) : MainViewModelFactory {
        return MainViewModelFactory(
            getCuratedPhotos = getCuratedPhotos,
            getFeaturedCollections = getFeaturedCollections,
            addPhotoToBookmarks = addPhotoToBookmarks,
            getPhotosFromBookmarks = getPhotosFromBookmarks
        )
    }

    @Provides
    fun provideFeaturedItems(): MutableList<Collection> = ArrayList()

    @Provides
    fun providePhotosItems(): MutableList<Photo> = ArrayList()
}
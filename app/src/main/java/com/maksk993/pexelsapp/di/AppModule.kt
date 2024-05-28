package com.maksk993.pexelsapp.di

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.maksk993.pexelsapp.domain.models.Collection
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.usecases.GetCuratedPhotos
import com.maksk993.pexelsapp.domain.usecases.GetFeaturedCollections
import com.maksk993.pexelsapp.presentation.screens.vm.MainViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {
    @Provides
    fun provideContext(): Context = context

    @Provides
    fun provideViewModelFactory(
        getCuratedPhotos: GetCuratedPhotos,
        getFeaturedCollections: GetFeaturedCollections
    ) : MainViewModelFactory {
        return MainViewModelFactory(
            getPhotos = getCuratedPhotos,
            getFeaturedCollections = getFeaturedCollections
        )
    }

    @Provides
    fun provideFeaturedItems(): MutableList<Collection> = ArrayList()

    @Provides
    fun providePhotosItems(): MutableList<Photo> = ArrayList()
}
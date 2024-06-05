package com.maksk993.pexelsapp.di

import android.content.Context
import com.maksk993.pexelsapp.domain.models.FeaturedCollection
import com.maksk993.pexelsapp.domain.models.Photo
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {
    @Provides
    fun provideContext(): Context = context

    @Provides
    fun provideFeaturedCollectionAdapterItems(): MutableList<FeaturedCollection> = ArrayList()

    @Provides
    fun providePhotoAdapterItems(): MutableList<Photo> = ArrayList()
}
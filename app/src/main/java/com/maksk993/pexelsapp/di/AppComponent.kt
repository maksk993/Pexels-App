package com.maksk993.pexelsapp.di

import com.maksk993.pexelsapp.presentation.screens.bookmarks.BookmarksFragment
import com.maksk993.pexelsapp.presentation.screens.details.DetailsFragment
import com.maksk993.pexelsapp.presentation.screens.home.HomeFragment
import com.maksk993.pexelsapp.presentation.screens.mainactivity.MainActivity
import dagger.Component

@Component(
    modules = [
    AppModule::class,
    DataModule::class,
])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(homeFragment: HomeFragment)
    fun inject(detailsFragment: DetailsFragment)
    fun inject(bookmarksFragment: BookmarksFragment)
}
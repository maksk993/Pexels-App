package com.maksk993.pexelsapp.presentation.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.maksk993.pexelsapp.presentation.screens.fragments.BookmarksFragment
import com.maksk993.pexelsapp.presentation.screens.fragments.DetailsFragment
import com.maksk993.pexelsapp.presentation.screens.fragments.HomeFragment

object Screens {
    fun Home() = FragmentScreen { HomeFragment() }
    fun Bookmarks() = FragmentScreen { BookmarksFragment() }
    fun Details() = FragmentScreen { DetailsFragment() }
}
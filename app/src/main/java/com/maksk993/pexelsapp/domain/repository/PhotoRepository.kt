package com.maksk993.pexelsapp.domain.repository

import com.maksk993.pexelsapp.domain.models.PhotoCallback
import com.maksk993.pexelsapp.domain.models.Title

interface PhotoRepository {
    fun getPhoto(title: Title, callback: PhotoCallback)
}
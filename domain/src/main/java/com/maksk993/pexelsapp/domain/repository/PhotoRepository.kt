package com.maksk993.pexelsapp.domain.repository

import com.maksk993.pexelsapp.domain.models.PhotoCallback
import com.maksk993.pexelsapp.domain.models.Collection

interface PhotoRepository {
    fun getPhoto(title: Collection, callback: PhotoCallback)
}
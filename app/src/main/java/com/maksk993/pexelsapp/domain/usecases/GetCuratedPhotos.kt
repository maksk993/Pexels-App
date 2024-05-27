package com.maksk993.pexelsapp.domain.usecases

import com.maksk993.pexelsapp.domain.models.PhotoCallback
import com.maksk993.pexelsapp.domain.models.Collection
import com.maksk993.pexelsapp.domain.repository.PhotoRepository

class GetCuratedPhotos(private val repository: PhotoRepository) {
    fun execute(title: Collection, callback: PhotoCallback) {
        repository.getPhoto(title, callback)
    }
}
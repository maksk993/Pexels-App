package com.maksk993.pexelsapp.domain.usecases

import com.maksk993.pexelsapp.domain.models.PhotoCallback
import com.maksk993.pexelsapp.domain.models.Title
import com.maksk993.pexelsapp.domain.repository.PhotoRepository

class GetCuratedPhotos(private val repository: PhotoRepository) {
    fun execute(title: Title, callback: PhotoCallback){
        repository.getPhoto(title, callback)
    }
}
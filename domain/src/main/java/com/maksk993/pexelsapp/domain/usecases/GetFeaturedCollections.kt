package com.maksk993.pexelsapp.domain.usecases

import com.maksk993.pexelsapp.domain.models.CollectionCallback
import com.maksk993.pexelsapp.domain.repository.CollectionRepository
import javax.inject.Inject

class GetFeaturedCollections @Inject constructor(val repository: CollectionRepository) {
    fun execute(callback: CollectionCallback) {
        repository.getFeaturedCollections(callback)
    }
}
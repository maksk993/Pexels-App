package com.maksk993.pexelsapp.domain.usecases

import com.maksk993.pexelsapp.domain.models.Collection
import com.maksk993.pexelsapp.domain.repository.CollectionRepository
import io.reactivex.Single
import javax.inject.Inject

class GetFeaturedCollections @Inject constructor(val repository: CollectionRepository) {
    fun execute(): Single<List<Collection>> = repository.getFeaturedCollections()
}
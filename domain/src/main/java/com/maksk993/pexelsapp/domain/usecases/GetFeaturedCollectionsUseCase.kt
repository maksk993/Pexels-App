package com.maksk993.pexelsapp.domain.usecases

import com.maksk993.pexelsapp.domain.models.FeaturedCollection
import com.maksk993.pexelsapp.domain.repository.CollectionRepository
import io.reactivex.Single
import javax.inject.Inject

class GetFeaturedCollectionsUseCase @Inject constructor(val repository: CollectionRepository) {
    fun execute(): Single<List<FeaturedCollection>> = repository.getFeaturedCollections()
}
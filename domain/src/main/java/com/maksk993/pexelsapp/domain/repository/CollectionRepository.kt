package com.maksk993.pexelsapp.domain.repository

import com.maksk993.pexelsapp.domain.models.FeaturedCollection
import io.reactivex.Single

interface CollectionRepository {
    fun getFeaturedCollections(): Single<List<FeaturedCollection>>
}
package com.maksk993.pexelsapp.domain.repository

import com.maksk993.pexelsapp.domain.models.Collection
import io.reactivex.Single

interface CollectionRepository {
    fun getFeaturedCollections(): Single<List<Collection>>
}
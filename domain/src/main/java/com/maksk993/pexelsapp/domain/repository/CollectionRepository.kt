package com.maksk993.pexelsapp.domain.repository

import com.maksk993.pexelsapp.domain.models.CollectionCallback

interface CollectionRepository {
    fun getFeaturedCollections(callback: CollectionCallback)
}
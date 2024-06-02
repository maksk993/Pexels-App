package com.maksk993.pexelsapp.domain.repository

import com.maksk993.pexelsapp.domain.models.Collection
import com.maksk993.pexelsapp.domain.models.Photo
import io.reactivex.Maybe

interface PhotoRepository {
    fun getPhoto(title: Collection): Maybe<List<Photo>>
}
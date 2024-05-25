package com.maksk993.pexelsapp.domain.usecases

import com.maksk993.pexelsapp.domain.models.TitleCallback
import com.maksk993.pexelsapp.domain.repository.TitleRepository

class GetFeaturedTitles(private val repository: TitleRepository) {
    fun execute(callback: TitleCallback) {
        repository.getFeaturedTitles(callback)
    }
}
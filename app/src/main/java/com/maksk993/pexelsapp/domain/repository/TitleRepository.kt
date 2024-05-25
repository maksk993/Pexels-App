package com.maksk993.pexelsapp.domain.repository

import com.maksk993.pexelsapp.domain.models.TitleCallback

interface TitleRepository {
    fun getFeaturedTitles(callback: TitleCallback)
}
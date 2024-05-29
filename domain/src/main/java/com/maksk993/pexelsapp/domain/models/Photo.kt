package com.maksk993.pexelsapp.domain.models

data class Photo(
    val id: Long,
    val src: Src,
    val photographer: String
) {
    data class Src(
        val original: String
    )
}


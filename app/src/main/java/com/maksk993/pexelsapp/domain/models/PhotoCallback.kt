package com.maksk993.pexelsapp.domain.models

interface PhotoCallback {
    fun call(photo: Photo)
}
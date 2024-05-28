package com.maksk993.pexelsapp.presentation.models

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.maksk993.pexelsapp.R

object GlideInstance {
    private val placeholder = R.drawable.placeholder

    fun loadImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
             .load(url)
             .placeholder(placeholder)
             .into(imageView)
    }
}
package com.maksk993.pexelsapp.presentation.models

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maksk993.pexelsapp.R

class PhotosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var image: ImageView
    var text: TextView
    init {
        image = view.findViewById(R.id.image)
        text = view.findViewById(R.id.text)
    }
}
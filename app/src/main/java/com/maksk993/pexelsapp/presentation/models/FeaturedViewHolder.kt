package com.maksk993.pexelsapp.presentation.models

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maksk993.pexelsapp.R

class FeaturedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var text: TextView
    init {
        text = view.findViewById(R.id.tv_item)
    }
}
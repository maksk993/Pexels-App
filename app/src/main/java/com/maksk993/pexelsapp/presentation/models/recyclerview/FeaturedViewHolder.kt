package com.maksk993.pexelsapp.presentation.models.recyclerview

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
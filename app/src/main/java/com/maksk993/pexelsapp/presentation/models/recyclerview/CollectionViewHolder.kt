package com.maksk993.pexelsapp.presentation.models.recyclerview

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maksk993.pexelsapp.R

class CollectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var text: TextView
    init {
        text = view.findViewById(R.id.tv_item)
    }

    fun setItemColors(backgroundColor: Drawable?, textColor: Int?){
        backgroundColor?.let { itemView.background = it }
        textColor?.let { text.setTextColor(it) }
    }
}
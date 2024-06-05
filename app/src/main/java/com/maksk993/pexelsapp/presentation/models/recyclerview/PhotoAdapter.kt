package com.maksk993.pexelsapp.presentation.models.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maksk993.pexelsapp.R
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.presentation.models.glide.GlideInstance
import javax.inject.Inject

class PhotoAdapter @Inject constructor(
    val context: Context,
    var items: MutableList<Photo>,
) : RecyclerView.Adapter<PhotoViewHolder>() {
    lateinit var listener: OnItemClickListener
    var setVisibilityGone: Boolean = false

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        GlideInstance.loadImage(context, items[position], holder.image)

        if (setVisibilityGone) holder.text.visibility = View.GONE
        else holder.text.text = items[position].photographer

        holder.itemView.setOnClickListener{
            listener.onItemClick(holder.adapterPosition)
        }
    }
}
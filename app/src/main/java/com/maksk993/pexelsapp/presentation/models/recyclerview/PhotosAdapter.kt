package com.maksk993.pexelsapp.presentation.models.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maksk993.pexelsapp.R
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.presentation.models.GlideInstance
import javax.inject.Inject

class PhotosAdapter @Inject constructor(
    val context: Context,
    var items: MutableList<Photo>,
) : RecyclerView.Adapter<PhotosViewHolder>() {
    private lateinit var listener: OnItemClickListener
    var setVisibilityGone: Boolean = false

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        return PhotosViewHolder(LayoutInflater.from(context).inflate(R.layout.item_photos, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        GlideInstance.loadImage(context, items[position].src.original, holder.image)

        if (setVisibilityGone) holder.text.visibility = View.GONE
        holder.itemView.setOnClickListener{
            listener.onItemClick(holder.adapterPosition)
        }
    }
}
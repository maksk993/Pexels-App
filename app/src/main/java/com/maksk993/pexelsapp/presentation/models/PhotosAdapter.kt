package com.maksk993.pexelsapp.presentation.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.maksk993.pexelsapp.R
import com.maksk993.pexelsapp.domain.models.Photo

class PhotosAdapter(private val context: Context,
                    private var items: List<Photo>,
                    private val setVisibilityGone: Boolean = false)
    : RecyclerView.Adapter<PhotosViewHolder>()
{
    private lateinit var listener: PhotosAdapter.OnItemClickListener

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
        Glide.with(context)
            .load(items[position].src.original)
            .placeholder(R.drawable.placeholder)
            .into(holder.image)

        if (setVisibilityGone) holder.text.visibility = View.GONE

        holder.itemView.setOnClickListener{
            listener.onItemClick(holder.adapterPosition)
        }
    }
}
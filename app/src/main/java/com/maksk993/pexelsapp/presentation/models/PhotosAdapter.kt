package com.maksk993.pexelsapp.presentation.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maksk993.pexelsapp.R

class PhotosAdapter(private val context: Context,
                    private var items: List<Int>,
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
        holder.image.setImageResource(items[position])
        if (setVisibilityGone) holder.text.visibility = View.GONE
        holder.itemView.setOnClickListener{
            listener.onItemClick(holder.adapterPosition)
        }
    }
}
package com.maksk993.pexelsapp.presentation.models.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maksk993.pexelsapp.R
import com.maksk993.pexelsapp.domain.models.Collection
import javax.inject.Inject

class CollectionAdapter @Inject constructor(
    val context: Context,
    var items: MutableList<Collection>
): RecyclerView.Adapter<CollectionViewHolder>() {
    lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int, submit: Boolean = true, setActive: Boolean = true, setQuery: Boolean = true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        return CollectionViewHolder(LayoutInflater.from(context).inflate(R.layout.item_collection, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        holder.text.text = items[holder.adapterPosition].title
        holder.itemView.setOnClickListener{
            listener.onItemClick(holder.adapterPosition)
            notifyDataSetChanged()
        }
    }
}
package com.maksk993.pexelsapp.presentation.models

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maksk993.pexelsapp.R

class FeaturedAdapter(private val context: Context, private var items: List<String>): RecyclerView.Adapter<FeaturedViewHolder>() {
    private lateinit var listener: OnItemClickListener
    private var selectedItem = -1

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedViewHolder {
        return FeaturedViewHolder(LayoutInflater.from(context).inflate(R.layout.featured_item, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FeaturedViewHolder, position: Int) {
        holder.text.text = items[holder.adapterPosition]
        holder.itemView.setOnClickListener{
            listener.onItemClick(holder.adapterPosition)
            selectedItem = holder.adapterPosition
            notifyDataSetChanged()
        }

        if (selectedItem == position) {
            holder.itemView.background = context.getDrawable(R.drawable.bg_featured_active)
            holder.text.setTextColor(context.getColor(R.color.white))
        }
        else {
            holder.itemView.background = context.getDrawable(R.drawable.bg_featured)
            holder.text.setTextColor(context.getColor(R.color.black))
        }
    }
}
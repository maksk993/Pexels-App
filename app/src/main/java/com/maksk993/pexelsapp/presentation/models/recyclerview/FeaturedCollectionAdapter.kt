package com.maksk993.pexelsapp.presentation.models.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maksk993.pexelsapp.R
import com.maksk993.pexelsapp.domain.models.FeaturedCollection
import javax.inject.Inject

class FeaturedCollectionAdapter @Inject constructor(
    val context: Context,
    var items: MutableList<FeaturedCollection>
): RecyclerView.Adapter<FeaturedCollectionViewHolder>() {
    lateinit var listener: OnItemClickListener
    private var selectedPosition = -1
    private val holderMap = HashMap<Int, FeaturedCollectionViewHolder>()

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedCollectionViewHolder {
        return FeaturedCollectionViewHolder(LayoutInflater.from(context).inflate(R.layout.item_featured_collection, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FeaturedCollectionViewHolder, position: Int) {
        holderMap[position] = holder
        holder.text.text = items[holder.adapterPosition].title
        holder.itemView.setOnClickListener{
            listener.onItemClick(holder.adapterPosition)
            selectedPosition = holder.adapterPosition
            notifyDataSetChanged()
        }

        if (selectedPosition == position) {
            holder.setItemColors(
                backgroundColor = context.getDrawable(R.drawable.bg_featured_collection_active),
                textColor = context.getColor(R.color.white)
            )
        }
        else {
            holder.setItemColors(
                backgroundColor = context.getDrawable(R.drawable.bg_featured_collection_inactive),
                textColor = context.getColor(R.color.black)
            )
        }
    }

    fun selectItem(query: String){
        if (!items.any { it.title == query }) {
            for (i in holderMap.values) i.setItemColors(
                backgroundColor = context.getDrawable(R.drawable.bg_featured_collection_inactive),
                textColor = context.getColor(R.color.black)
            )
            selectedPosition = -1
            return
        }
        for (i in items.indices){
            if (items[i].title == query) {
                holderMap[i]?.setItemColors(
                    backgroundColor = context.getDrawable(R.drawable.bg_featured_collection_active),
                    textColor = context.getColor(R.color.white)
                )
                selectedPosition = i
                return
            }
        }
        selectedPosition = -1
    }

}
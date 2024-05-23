package com.maksk993.pexelsapp.presentation.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.maksk993.pexelsapp.databinding.FragmentHomeBinding
import com.maksk993.pexelsapp.presentation.models.FeaturedAdapter
import java.util.ArrayList

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private lateinit var adapter: FeaturedAdapter
    private val items: MutableList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.rvFeatured.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        adapter = FeaturedAdapter(requireContext(), items)
        adapter.setOnItemClickListener(object : FeaturedAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {

            }
        })
        binding.rvFeatured.adapter = adapter

        addItemToFeatured("Ice")
        addItemToFeatured("Watches")
        addItemToFeatured("Drawing")
        addItemToFeatured("Something")
        addItemToFeatured("Else")
        addItemToFeatured("Test")
        addItemToFeatured("Recycler")

        return binding.root
    }

    private fun addItemToFeatured(text: String){
        items.add(text)
        adapter.notifyItemInserted(items.size)
    }

}
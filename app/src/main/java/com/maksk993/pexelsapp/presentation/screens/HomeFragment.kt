package com.maksk993.pexelsapp.presentation.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.maksk993.pexelsapp.R
import com.maksk993.pexelsapp.databinding.FragmentHomeBinding
import com.maksk993.pexelsapp.presentation.models.FeaturedAdapter
import com.maksk993.pexelsapp.presentation.models.PhotosAdapter
import com.maksk993.pexelsapp.presentation.navigation.Screens
import java.util.ArrayList

class HomeFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding

    private lateinit var featuredAdapter: FeaturedAdapter
    private val featuredItems: MutableList<String> = ArrayList()
    private lateinit var photosAdapter: PhotosAdapter
    private val photosItems: MutableList<Int> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initSearchView()
        initFeaturedRv()
        initPhotosRv()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addItemToFeatured("Ice")
        addItemToFeatured("Watches")
        addItemToFeatured("Drawing")
        addItemToFeatured("Something")
        addItemToFeatured("Else")
        addItemToFeatured("Test")
        addItemToFeatured("Recycler")

        addItemToPhotos(R.drawable.a1)
    }

    private fun initSearchView() {
        binding.searchView.findViewById<TextView>(androidx.appcompat.R.id.search_src_text).apply {
            typeface = resources.getFont(R.font.mulish)
            textSize = 15F
        }
    }

    private fun initPhotosRv() {
        binding.rvPhotos.layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL
        )
        photosAdapter = PhotosAdapter(requireContext(), photosItems, true)
        photosAdapter.setOnItemClickListener(object : PhotosAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                viewModel.replaceScreen(Screens.Details())
            }
        })
        binding.rvPhotos.adapter = photosAdapter
    }

    private fun initFeaturedRv() {
        binding.rvFeatured.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        featuredAdapter = FeaturedAdapter(requireContext(), featuredItems)
        binding.rvFeatured.adapter = featuredAdapter
    }

    private fun addItemToPhotos(image: Int){
        photosItems.add(image)
        photosAdapter.notifyItemInserted(photosItems.size)
    }

    private fun addItemToFeatured(text: String){
        featuredItems.add(text)
        featuredAdapter.notifyItemInserted(featuredItems.size)
    }

}
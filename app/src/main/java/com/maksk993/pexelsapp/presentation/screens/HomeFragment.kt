package com.maksk993.pexelsapp.presentation.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.maksk993.pexelsapp.R
import com.maksk993.pexelsapp.databinding.FragmentHomeBinding
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.models.Collection
import com.maksk993.pexelsapp.presentation.models.FeaturedAdapter
import com.maksk993.pexelsapp.presentation.models.FeaturedViewHolder
import com.maksk993.pexelsapp.presentation.models.PhotosAdapter
import com.maksk993.pexelsapp.presentation.navigation.Screens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.ArrayList

class HomeFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding

    private lateinit var featuredAdapter: FeaturedAdapter
    private val featuredItems: MutableList<Collection> = ArrayList()
    private lateinit var photosAdapter: PhotosAdapter
    private val photosItems: MutableList<Photo> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initSearchView()
        initFeaturedRv()
        initPhotosRv()
        initStub()
        initNetworkStub()

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        viewModel.getPhotos()
    }

    private fun initObservers() {
        viewModel.apply {
           featuredCollections.observe(viewLifecycleOwner){
                for (i in it){
                    addItemToFeatured(i)
                }
            }

            photos.observe(viewLifecycleOwner){
                with(binding) {
                    if (it == null) {
                        rvPhotos.visibility = View.GONE
                        stub.visibility = View.GONE
                        networkStub.visibility = View.VISIBLE
                    }
                    else if (it.isEmpty()) {
                        rvPhotos.visibility = View.GONE
                        networkStub.visibility = View.GONE
                        stub.visibility = View.VISIBLE
                    } else {
                        stub.visibility = View.GONE
                        networkStub.visibility = View.GONE
                        rvPhotos.visibility = View.VISIBLE
                        for (i in it) {
                            addItemToPhotos(i)
                        }
                    }
                }
            }
        }
    }

    private fun initSearchView() {
        binding.searchView.findViewById<TextView>(androidx.appcompat.R.id.search_src_text).apply {
            typeface = resources.getFont(R.font.mulish)
            textSize = 15F
        }

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    clearRv()
                    viewModel.getPhotos(Collection(query))
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = true
        })
    }

    private fun initPhotosRv() {
        binding.rvPhotos.layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL
        )
        photosAdapter = PhotosAdapter(requireContext(), photosItems, true)
        photosAdapter.setOnItemClickListener(object : PhotosAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                viewModel.setFocusedPhoto(photosItems[position])
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
        featuredAdapter.setOnItemClickListener(object : FeaturedAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                binding.searchView.setQuery(featuredItems[position].title, true)
            }
        })
        binding.rvFeatured.adapter = featuredAdapter
    }

    private fun clearRv() {
        photosItems.clear()
        photosAdapter.notifyDataSetChanged()
    }

    private fun addItemToPhotos(photo: Photo){
        if (photosItems.contains(photo)) return
        photosItems.add(photo)
        photosAdapter.notifyItemInserted(photosItems.size)
    }

    private fun addItemToFeatured(collection: Collection){
        if (featuredItems.contains(collection)) return
        featuredItems.add(collection)
        featuredAdapter.notifyItemInserted(featuredItems.size)
    }

    private fun initStub() {
        with(binding) {
            explore.setOnClickListener{
                viewModel.getPhotos()
                searchView.setQuery("", false)
            }
        }
    }

    private fun initNetworkStub(){
        with(binding) {
            tryAgain.setOnClickListener{
                if (searchView.query.toString().isEmpty()) viewModel.getPhotos()
                else viewModel.getPhotos(Collection(searchView.query.toString()))
            }
        }
    }

}
package com.maksk993.pexelsapp.presentation.screens.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.maksk993.pexelsapp.R
import com.maksk993.pexelsapp.app.App
import com.maksk993.pexelsapp.databinding.FragmentHomeBinding
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.domain.models.Collection
import com.maksk993.pexelsapp.presentation.models.recyclerview.FeaturedAdapter
import com.maksk993.pexelsapp.presentation.models.recyclerview.PhotosAdapter
import com.maksk993.pexelsapp.presentation.navigation.Screens
import com.maksk993.pexelsapp.presentation.screens.vm.MainViewModel
import com.maksk993.pexelsapp.presentation.screens.vm.MainViewModelFactory
import javax.inject.Inject

class HomeFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: MainViewModelFactory
    private val viewModel: MainViewModel by activityViewModels { viewModelFactory }

    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var featuredAdapter: FeaturedAdapter
    @Inject
    lateinit var photosAdapter: PhotosAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as App).appComponent.inject(this)
    }

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
                        Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show()
                        rvPhotos.visibility = View.GONE
                        stub.visibility = View.GONE
                        networkStub.visibility = View.VISIBLE
                    }
                    else if (it.isEmpty()) {
                        rvPhotos.visibility = View.GONE
                        networkStub.visibility = View.GONE
                        stub.visibility = View.VISIBLE
                    }
                    else {
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
        photosAdapter.setVisibilityGone = true
        photosAdapter.setOnItemClickListener(object : PhotosAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                viewModel.setFocusedPhoto(photosAdapter.items[position])
                viewModel.navigateToScreen(Screens.Details())
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
       // featuredAdapter = FeaturedAdapter(requireContext(), featuredItems)
        featuredAdapter.setOnItemClickListener(object : FeaturedAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                binding.searchView.setQuery(featuredAdapter.items[position].title, true)
            }
        })
        binding.rvFeatured.adapter = featuredAdapter
    }

    private fun clearRv() {
        photosAdapter.items.clear()
        photosAdapter.notifyDataSetChanged()
    }

    private fun addItemToPhotos(photo: Photo){
        if (photosAdapter.items.contains(photo)) return
        photosAdapter.items.add(photo)
        photosAdapter.notifyItemInserted(photosAdapter.items.size)
    }

    private fun addItemToFeatured(collection: Collection){
        if (featuredAdapter.items.contains(collection)) return
        featuredAdapter.items.add(collection)
        featuredAdapter.notifyItemInserted(featuredAdapter.items.size)
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
                viewModel.getFeaturedCollections()
                if (searchView.query.toString().isEmpty()) viewModel.getPhotos()
                else viewModel.getPhotos(Collection(searchView.query.toString()))
            }
        }
    }

}
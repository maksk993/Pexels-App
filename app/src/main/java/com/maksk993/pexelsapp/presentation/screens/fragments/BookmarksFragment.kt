package com.maksk993.pexelsapp.presentation.screens.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.maksk993.pexelsapp.app.App
import com.maksk993.pexelsapp.databinding.FragmentBookmarksBinding
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.presentation.models.recyclerview.PhotosAdapter
import com.maksk993.pexelsapp.presentation.screens.vm.MainViewModel
import com.maksk993.pexelsapp.presentation.screens.vm.MainViewModelFactory
import javax.inject.Inject

class BookmarksFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: MainViewModelFactory
    private val viewModel: MainViewModel by activityViewModels { viewModelFactory }

    private lateinit var binding: FragmentBookmarksBinding

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
        binding = FragmentBookmarksBinding.inflate(inflater, container, false)

        initPhotosRv()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun initPhotosRv() {
        binding.rvPhotos.layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL
        )
        photosAdapter = PhotosAdapter(requireContext(), photosAdapter.items)
        binding.rvPhotos.adapter = photosAdapter
    }

    private fun addItemToPhotos(photo: Photo){
        photosAdapter.items.add(photo)
        photosAdapter.notifyItemInserted(photosAdapter.items.size)
    }

}
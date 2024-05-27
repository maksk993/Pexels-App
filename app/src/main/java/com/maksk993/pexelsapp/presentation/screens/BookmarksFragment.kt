package com.maksk993.pexelsapp.presentation.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.maksk993.pexelsapp.databinding.FragmentBookmarksBinding
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.presentation.models.PhotosAdapter
import java.util.ArrayList

class BookmarksFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentBookmarksBinding

    private lateinit var photosAdapter: PhotosAdapter
    private val photosItems: MutableList<Photo> = ArrayList()

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
        //addItemToPhotos(R.drawable.a1)
        //addItemToPhotos(R.drawable.a2)
    }

    private fun initPhotosRv() {
        binding.rvPhotos.layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL
        )
        photosAdapter = PhotosAdapter(requireContext(), photosItems)
        binding.rvPhotos.adapter = photosAdapter
    }

    private fun addItemToPhotos(photo: Photo){
        photosItems.add(photo)
        photosAdapter.notifyItemInserted(photosItems.size)
    }

}
package com.maksk993.pexelsapp.presentation.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.maksk993.pexelsapp.R
import com.maksk993.pexelsapp.databinding.FragmentBookmarksBinding
import com.maksk993.pexelsapp.presentation.models.PhotosAdapter
import java.util.ArrayList

class BookmarksFragment : Fragment() {
    private lateinit var binding: FragmentBookmarksBinding

    private lateinit var photosAdapter: PhotosAdapter
    private val photosItems: MutableList<Int> = ArrayList()

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
        addItemToPhotos(R.drawable.a1)
        //addItemToPhotos(R.drawable.a2)
    }

    private fun initPhotosRv() {
        binding.rvPhotos.layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL
        )
        photosAdapter = PhotosAdapter(requireContext(), photosItems)
        binding.rvPhotos.adapter = photosAdapter
    }

    private fun addItemToPhotos(image: Int){
        photosItems.add(image)
        photosAdapter.notifyItemInserted(photosItems.size)
    }

}
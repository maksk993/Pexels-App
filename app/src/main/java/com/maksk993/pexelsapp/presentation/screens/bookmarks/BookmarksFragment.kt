package com.maksk993.pexelsapp.presentation.screens.bookmarks

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.maksk993.pexelsapp.app.App
import com.maksk993.pexelsapp.databinding.FragmentBookmarksBinding
import com.maksk993.pexelsapp.presentation.models.recyclerview.PhotosAdapter
import com.maksk993.pexelsapp.presentation.navigation.NavigationManager
import com.maksk993.pexelsapp.presentation.navigation.Screens
import javax.inject.Inject

class BookmarksFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: BookmarksViewModelFactory
    private val viewModel: BookmarksViewModel by viewModels { viewModelFactory }

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
        initStub()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        viewModel.getPhotosFromBookmarks()
    }

    private fun initObservers() {
        viewModel.bookmarks.observe(viewLifecycleOwner){
            with(binding){
                if (it.isEmpty()) {
                    rvBookmarks.visibility = View.GONE
                    bookmarksStub.visibility = View.VISIBLE
                }
                else {
                    bookmarksStub.visibility = View.GONE
                    rvBookmarks.visibility = View.VISIBLE
                    for (i in it) i?.let { addItemToPhotos(i) }
                }
            }
        }
    }

    private fun initStub() {
        binding.explore.setOnClickListener{
            activity?.onBackPressed()
        }
    }

    private fun initPhotosRv() {
        binding.rvBookmarks.layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL
        )
        photosAdapter.setOnItemClickListener(object : PhotosAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                NavigationManager.setFocusedPhoto(photosAdapter.items[position])
                NavigationManager.navigateToScreen(Screens.Details())
            }
        })
        binding.rvBookmarks.adapter = photosAdapter
    }

    private fun addItemToPhotos(photo: com.maksk993.pexelsapp.domain.models.Photo){
        if (photosAdapter.items.contains(photo)) return
        photosAdapter.items.add(photo)
        photosAdapter.notifyItemInserted(photosAdapter.items.size)
    }

}
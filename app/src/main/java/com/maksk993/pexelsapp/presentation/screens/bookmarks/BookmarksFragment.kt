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
import com.maksk993.pexelsapp.presentation.models.recyclerview.PhotoAdapter
import com.maksk993.pexelsapp.presentation.navigation.NavigationManager
import com.maksk993.pexelsapp.presentation.navigation.Screens
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BookmarksFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: BookmarksViewModelFactory
    private val viewModel: BookmarksViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentBookmarksBinding

    @Inject
    lateinit var photoAdapter: PhotoAdapter

    private lateinit var disposable: Disposable

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
        viewModel.getPhotosFromBookmarks()
        initObservers()
    }

    private fun initObservers() {
        viewModel.bookmarks.observe(viewLifecycleOwner){
            binding.apply {
                if (it.isEmpty()) {
                    rvBookmarks.visibility = View.GONE
                    bookmarksStub.visibility = View.VISIBLE
                }
                else {
                    bookmarksStub.visibility = View.GONE
                    rvBookmarks.visibility = View.VISIBLE
                    for (i in it) i?.let { addItemToPhotos(i) }
                    removeDeletedPhotos()
                }
            }
        }

        viewModel.shouldProgressBarBeVisible.observe(viewLifecycleOwner){
            binding.apply {
                if (it) {
                    progressBar.progress = 0
                    progressBar.visibility = View.VISIBLE
                    rvBookmarks.visibility = View.GONE
                    bookmarksStub.visibility = View.GONE
                }
                else {
                    disposable = Observable.interval(30, TimeUnit.MILLISECONDS)
                        .takeWhile { progressBar.progress < 100 }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { progressBar.incrementProgressBy(10) },
                            { throwable -> throwable.printStackTrace() },
                            { progressBar.visibility = View.GONE }
                        )
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
        photoAdapter.listener = object : PhotoAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                NavigationManager.setFocusedPhoto(photoAdapter.items[position])
                NavigationManager.navigateToScreen(Screens.Details())
            }
        }
        binding.rvBookmarks.adapter = photoAdapter
    }

    private fun addItemToPhotos(photo: com.maksk993.pexelsapp.domain.models.Photo){
        if (photoAdapter.items.contains(photo)) return
        photoAdapter.items.add(photo)
        photoAdapter.notifyItemInserted(photoAdapter.items.size)
    }

    private fun removeDeletedPhotos(){
        for (i in photoAdapter.items.indices) {
            if (viewModel.bookmarks.value?.contains(photoAdapter.items[i]) == false) {
                photoAdapter.items.removeAt(i)
                photoAdapter.notifyItemRemoved(i)
                return
            }
        }
    }

    override fun onDestroy() {
        if (::disposable.isInitialized) disposable.dispose()
        super.onDestroy()
    }
}
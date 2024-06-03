package com.maksk993.pexelsapp.presentation.screens.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.maksk993.pexelsapp.R
import com.maksk993.pexelsapp.app.App
import com.maksk993.pexelsapp.databinding.FragmentHomeBinding
import com.maksk993.pexelsapp.domain.models.Collection
import com.maksk993.pexelsapp.domain.models.Photo
import com.maksk993.pexelsapp.presentation.models.recyclerview.CollectionAdapter
import com.maksk993.pexelsapp.presentation.models.recyclerview.CollectionViewHolder
import com.maksk993.pexelsapp.presentation.models.recyclerview.PhotoAdapter
import com.maksk993.pexelsapp.presentation.navigation.NavigationManager
import com.maksk993.pexelsapp.presentation.navigation.Screens
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: HomeViewModelFactory
    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var collectionAdapter: CollectionAdapter
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
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initSearchView()
        initCollectionsRv()
        initPhotosRv()
        initStub()
        initNetworkStub()

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        viewModel.apply {
           featuredCollections.observe(viewLifecycleOwner){
                for (i in it){
                    addItemToFeatured(i)
                }
            }

            photos.observe(viewLifecycleOwner){
                binding.apply {
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

            shouldProgressBarBeVisible.observe(viewLifecycleOwner){
                binding.apply {
                    if (it) {
                        progressBar.progress = 0
                        progressBar.visibility = View.VISIBLE
                        rvPhotos.visibility = View.GONE
                        stub.visibility = View.GONE
                        networkStub.visibility = View.GONE
                    }
                    else {
                        disposable = Observable.interval(50, TimeUnit.MILLISECONDS)
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
    }

    private fun initSearchView() {
        binding.searchView.findViewById<TextView>(androidx.appcompat.R.id.search_src_text).apply {
            typeface = resources.getFont(R.font.mulish)
            textSize = 15F
        }

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    for (i in collectionAdapter.items.indices) {
                        if (collectionAdapter.items[i].title == binding.searchView.query.toString()){
                            collectionAdapter.listener.onItemClick(position = i, submit = false, setActive = true, setQuery = true)
                        }
                        else collectionAdapter.listener.onItemClick(position = i, submit = false, setActive = false, setQuery = false)
                    }
                    clearRv()
                    if (query == "") viewModel.getPhotos()
                    else viewModel.getPhotos(Collection(query))
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                for (i in collectionAdapter.items.indices) {
                    if (collectionAdapter.items[i].title == binding.searchView.query.toString())
                        return false
                }
                return onQueryTextSubmit(newText)
            }
        })
    }

    private fun initPhotosRv() {
        binding.rvPhotos.layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL
        )
        photoAdapter.setVisibilityGone = true
        photoAdapter.listener = object : PhotoAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                NavigationManager.setFocusedPhoto(photoAdapter.items[position])
                NavigationManager.navigateToScreen(Screens.Details())
            }
        }
        binding.rvPhotos.adapter = photoAdapter
    }

    private fun initCollectionsRv() {
        binding.rvCollections.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        collectionAdapter.listener = object : CollectionAdapter.OnItemClickListener{
            override fun onItemClick(position: Int, submit: Boolean, setActive: Boolean, setQuery: Boolean) {
                if (setQuery) binding.searchView.setQuery(collectionAdapter.items[position].title, submit)
                val holder = binding.rvCollections.findViewHolderForAdapterPosition(position)
                if (holder is CollectionViewHolder){
                    if (setActive)
                        holder.setItemColors(
                            context?.getDrawable(R.drawable.bg_featured_collection_active),
                            context?.getColor(R.color.white)
                        )
                    else
                        holder.setItemColors(
                            context?.getDrawable(R.drawable.bg_featured_collection_inactive),
                            context?.getColor(R.color.black)
                        )
                }
            }
        }
        binding.rvCollections.adapter = collectionAdapter
    }

    private fun clearRv() {
        photoAdapter.items.clear()
        photoAdapter.notifyDataSetChanged()
    }

    private fun addItemToPhotos(photo: Photo){
        if (photoAdapter.items.contains(photo)) return
        photoAdapter.items.add(photo)
        photoAdapter.notifyItemInserted(photoAdapter.items.size)
    }

    private fun addItemToFeatured(collection: Collection){
        if (collectionAdapter.items.contains(collection)) return
        collectionAdapter.items.add(collection)
        collectionAdapter.notifyItemInserted(collectionAdapter.items.size)
    }

    private fun initStub() {
        binding.apply {
            explore.setOnClickListener{
                viewModel.getPhotos()
                searchView.setQuery("", false)
            }
        }
    }

    private fun initNetworkStub(){
        binding.apply {
            tryAgain.setOnClickListener{
                viewModel.getFeaturedCollections()
                if (searchView.query.toString().isEmpty()) viewModel.getPhotos()
                else viewModel.getPhotos(Collection(searchView.query.toString()))
            }
        }
    }

    override fun onDestroy() {
        if (::disposable.isInitialized) disposable.dispose()
        super.onDestroy()
    }
}
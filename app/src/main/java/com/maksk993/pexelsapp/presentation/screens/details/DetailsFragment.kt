package com.maksk993.pexelsapp.presentation.screens.details

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.maksk993.pexelsapp.app.App
import com.maksk993.pexelsapp.databinding.FragmentDetailsBinding
import com.maksk993.pexelsapp.presentation.models.GlideInstance
import com.maksk993.pexelsapp.presentation.navigation.NavigationManager
import com.maksk993.pexelsapp.presentation.navigation.Screens
import javax.inject.Inject

class DetailsFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: DetailsViewModelFactory
    private val viewModel: DetailsViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentDetailsBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        initButtons()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        NavigationManager.focusedPhoto.value?.let {
            viewModel.wasPhotoAddedToBookmarks(NavigationManager.focusedPhoto.value!!)
        }
    }

    private fun initObservers() {
        NavigationManager.focusedPhoto.observe(viewLifecycleOwner){
            val listener = object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                initStub()
                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean = false
        }
            GlideInstance.loadImage(requireContext(), it, binding.image, listener)
            binding.headerTitle.text = it.photographer
        }

        viewModel.wasAdded.observe(viewLifecycleOwner){
            binding.btnAddToBookmarks.isActivated = it
        }
    }

    private fun initButtons() {
        binding.apply {
            btnBack.setOnClickListener{
                activity?.onBackPressed()
            }

            btnAddToBookmarks.setOnClickListener {
                if (btnAddToBookmarks.isActivated) {
                    NavigationManager.focusedPhoto.value?.let {
                        viewModel.deletePhotoFromBookmarks(it)
                    }
                    btnAddToBookmarks.isActivated = false
                }
                else {
                    NavigationManager.focusedPhoto.value?.let {
                        viewModel.addPhotoToBookmarks(it)
                    }
                    btnAddToBookmarks.isActivated = true
                }
            }

            btnDownload.setOnClickListener {
                NavigationManager.focusedPhoto.value?.let {
                    GlideInstance.downloadToStorage(requireContext(), it)
                }
            }

            tvDownload.setOnClickListener {
                btnDownload.performClick()
            }
        }
    }

    private fun initStub() {
        binding.apply {
            scrollView.isFillViewport = true
            footer.visibility = View.GONE
            cardView.visibility = View.GONE
            detailsStub.visibility = View.VISIBLE
            explore.setOnClickListener{
                NavigationManager.backToScreen(Screens.Home())
            }
        }
    }
}
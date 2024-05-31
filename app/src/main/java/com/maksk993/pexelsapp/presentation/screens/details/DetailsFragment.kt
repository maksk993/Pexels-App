package com.maksk993.pexelsapp.presentation.screens.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.maksk993.pexelsapp.app.App
import com.maksk993.pexelsapp.databinding.FragmentDetailsBinding
import com.maksk993.pexelsapp.presentation.models.GlideInstance
import com.maksk993.pexelsapp.presentation.navigation.NavigationManager
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
            GlideInstance.loadImage(requireContext(), it.src.original, binding.image)
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
                NavigationManager.focusedPhoto.value?.let {
                    viewModel.addPhotoToBookmarks(NavigationManager.focusedPhoto.value!!)
                }
                btnAddToBookmarks.isActivated = true
            }
        }
    }

}
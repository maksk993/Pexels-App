package com.maksk993.pexelsapp.presentation.screens.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.maksk993.pexelsapp.app.App
import com.maksk993.pexelsapp.databinding.FragmentDetailsBinding
import com.maksk993.pexelsapp.presentation.models.GlideInstance
import com.maksk993.pexelsapp.presentation.navigation.Screens
import com.maksk993.pexelsapp.presentation.vm.MainViewModel
import com.maksk993.pexelsapp.presentation.vm.MainViewModelFactory
import javax.inject.Inject

class DetailsFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: MainViewModelFactory
    private val viewModel: MainViewModel by activityViewModels { viewModelFactory }

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
    }

    private fun initObservers() {
        viewModel.focusedPhoto.observe(viewLifecycleOwner){
            GlideInstance.loadImage(requireContext(), it.src.original, binding.image)
            binding.headerTitle.text = it.photographer
        }
    }

    private fun initButtons() {
        binding.apply {
            btnBack.setOnClickListener{
                viewModel.backToScreen(Screens.Home())
            }

            btnAddToBookmarks.setOnClickListener {
                viewModel.addPhotoToBookmarks()
            }
        }
    }

}
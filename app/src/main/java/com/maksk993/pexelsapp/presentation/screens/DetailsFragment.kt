package com.maksk993.pexelsapp.presentation.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.maksk993.pexelsapp.R
import com.maksk993.pexelsapp.databinding.FragmentDetailsBinding
import com.maksk993.pexelsapp.presentation.navigation.Screens

class DetailsFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        initButtons()

        return binding.root
    }

    private fun initButtons() {
        binding.btnBack.setOnClickListener{
            viewModel.replaceScreen(Screens.Home())
        }
    }

}
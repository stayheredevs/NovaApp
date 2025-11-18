package com.zeroone.novaapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zeroone.novaapp.databinding.FragmentAssetsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAssets: Fragment() {

    lateinit var binding: FragmentAssetsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAssetsBinding.inflate(inflater, container, false)

        return binding.root
    }
}
package com.zeroone.novaapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zeroone.novaapp.databinding.FragmentAdminAccountBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAccount: Fragment() {

    lateinit var binding: FragmentAdminAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAdminAccountBinding.inflate(inflater, container, false)

        return binding.root
    }
}
package com.example.unidorm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.unidorm.R
import com.example.unidorm.databinding.FragmentAddressInfoBinding
import com.example.unidorm.databinding.FragmentNotificationBinding


class AddressInfoFragment : Fragment() {

    private var _binding: FragmentAddressInfoBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddressInfoBinding.inflate(inflater, container, false)




        return binding.root
    }

}
package com.example.unidorm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.unidorm.databinding.FragmentAddressInfoBinding
import com.example.unidorm.databinding.FragmentChosenDormInfoBinding


class ChosenDormInfoFragment : Fragment() {

    private var _binding: FragmentChosenDormInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChosenDormInfoBinding.inflate(inflater, container, false)




        return binding.root
    }

}
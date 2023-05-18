package com.example.unidorm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment


import com.example.unidorm.R
import com.example.unidorm.databinding.FragmentShopBinding
import com.google.firebase.database.FirebaseDatabase


class ShopFragment : Fragment() {
    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    var openStatusDropDownListOnShop = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentShopBinding.inflate(inflater, container, false)


        binding.searchBtn.setOnClickListener{
            setVisibility()
        }


        val options = listOf("Option 1", "Option 2", "Option 3")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.dropdownDormSortMenu.adapter = adapter

        binding.dropdownDormSortMenu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> Toast.makeText(requireContext(), "Option 1", Toast.LENGTH_SHORT).show()
                    1 -> Toast.makeText(requireContext(), "Option 2", Toast.LENGTH_SHORT).show()
                    2 -> Toast.makeText(requireContext(), "Option 3", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Ничего не выбрано
            }
        }

        return binding.root
    }

    private fun setVisibility()
    {
        if (openStatusDropDownListOnShop) {
            binding.dropdownDormSortMenu.visibility = View.GONE
            binding.searchLayout.visibility = View.GONE
            //binding.searchBtn.visibility = View.VISIBLE
            openStatusDropDownListOnShop = false
        }
        else{
            binding.dropdownDormSortMenu.visibility = View.VISIBLE
            binding.searchLayout.visibility = View.VISIBLE
            //binding.searchBtn.visibility = View.GONE
            openStatusDropDownListOnShop = true
        }
    }

}

package com.example.unidorm.fragments

import ShopItemAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.google.firebase.database.*

import com.example.unidorm.databinding.FragmentShopBinding
import com.example.unidorm.fragments.contract.navigator

import com.example.unidorm.model.dbModel.ShopItemModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener



class ShopFragment : Fragment() {
    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbRef: DatabaseReference


    private lateinit var adapter: ShopItemAdapter

    private var selectedItem: ShopItemModel? = null
    private var shItemList = mutableListOf<ShopItemModel>()
    private var filteredItems = mutableListOf<ShopItemModel>()


    var openStatusDropDownListOnShop = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentShopBinding.inflate(inflater, container, false)


        binding.searchBtn.setOnClickListener {
            setVisibility()
        }
        binding.addItemBtn.setOnClickListener{
            navigator().showItemSellScreen()
        }


        val options = listOf("Общежитие 1", "Общежитие 2", "Общежитие 3")
        val adapter1 = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.dropdownDormSortMenu.adapter = adapter1

        binding.dropdownDormSortMenu.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> Toast.makeText(requireContext(), "Общежитие 1", Toast.LENGTH_SHORT).show()
                        1 -> Toast.makeText(requireContext(), "Общежитие 2", Toast.LENGTH_SHORT).show()
                        2 -> Toast.makeText(requireContext(), "Общежитие 3", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Ничего не выбрано
                }
            }

        readItemDB()

        binding.searchView.clearFocus()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {

                filteredItems.clear()
                adapter.clear()

                if (newText.isEmpty()) {
                    filteredItems.addAll(shItemList)
                } else {
                    for (shItem in shItemList) {
                        if (shItem.Item?.contains(newText, ignoreCase = true) == true) {
                            filteredItems.add(shItem)
                        }
                    }
                }

                adapter = ShopItemAdapter(filteredItems)
                { filteredItems ->
                    selectedItem = filteredItems
                    val fragment = ItemInShopFragment()
                    val bundle = Bundle()
                    bundle.putParcelable("item", selectedItem)
                    fragment.arguments = bundle
                    navigator().showItemInShopFragment(fragment)

                }
                binding.itemShopListRView.adapter = adapter

                return true
            }
        })

        return binding.root
    }

    override fun onPause() {
        filteredItems.clear()
        shItemList.clear()
        if(!filteredItems.isEmpty() && !shItemList.isEmpty())
        adapter.clear()
        super.onPause()
    }


    private fun setVisibility() {
        if (openStatusDropDownListOnShop) {
            binding.searchLayout.visibility = View.GONE
            //binding.searchBtn.visibility = View.VISIBLE
            openStatusDropDownListOnShop = false
        } else {
            binding.searchLayout.visibility = View.VISIBLE
            //binding.listView.
            //binding.searchBtn.visibility = View.GONE
            openStatusDropDownListOnShop = true
        }
    }
    private fun readItemDB()
    {
        dbRef = FirebaseDatabase.getInstance().getReference("ShopItem")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                binding.itemShopListRView.layoutManager = LinearLayoutManager(requireContext())

                for (snapshot in dataSnapshot.children) {
                    val shItem = snapshot.getValue(ShopItemModel::class.java)

                    if (shItem != null) {

                        shItemList.add(shItem)

                    }
                }

                filteredItems.addAll(shItemList)
                adapter = ShopItemAdapter(filteredItems)
                { filteredItems ->
                    selectedItem = filteredItems // Сохранение выбранного элемента в переменной selectedItem
                    val fragment = ItemInShopFragment()
                    val bundle = Bundle()
                    bundle.putParcelable("item", selectedItem)
                    fragment.arguments = bundle
                    navigator().showItemInShopFragment(fragment)

                }
                binding.itemShopListRView.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

}

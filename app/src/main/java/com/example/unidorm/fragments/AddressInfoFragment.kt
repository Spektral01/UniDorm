package com.example.unidorm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.unidorm.databinding.FragmentAddressInfoBinding
import com.example.unidorm.fragments.contract.navigator
import com.example.unidorm.model.dbModel.DBHandler
import com.example.unidorm.model.dbModel.DormitoryModel


class AddressInfoFragment : Fragment() {

    private var _binding: FragmentAddressInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ArrayAdapter<String>
    private val dbHandl = DBHandler()
    private var dormList = mutableListOf<DormitoryModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddressInfoBinding.inflate(inflater, container, false)


        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, arrayListOf())
        binding.listView.adapter = adapter

        dbHandl.readAddressInfo(adapter, dormList)

        binding.listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String

            for (dormOpen in dormList){
                if(dormOpen.dormName==selectedItem){
                    val fragment = ChosenDormInfoFragment()
                    val bundle = Bundle()
                    bundle.putParcelable("dorm", dormOpen)
                    fragment.arguments = bundle
                    navigator().showItemInShopFragment(fragment)
                }
            }


        }


        return binding.root
    }

}
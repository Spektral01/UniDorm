package com.example.unidorm.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.unidorm.databinding.FragmentNotificationBinding
import com.example.unidorm.model.dbModel.DBHandler
import com.example.unidorm.fragments.contract.navigator
import com.example.unidorm.model.dbModel.NotificationModel
import com.example.unidorm.model.dbModel.ShopItemModel
import com.google.firebase.storage.FirebaseStorage

class NotificationFragment : Fragment(){

    private var _binding:FragmentNotificationBinding? = null
    private val binding get() = _binding!!
    //private lateinit var dbRef: DatabaseReference
    private lateinit var adapter: ArrayAdapter<String>
    private val dbHandl = DBHandler()
    private var notifList = mutableListOf<NotificationModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)

        binding.floatingActBntCreateNotif.setOnClickListener{
            navigator().showCreateNotificationScreen()
        }

        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, arrayListOf())
        binding.listView.adapter = adapter

        dbHandl.readNotificationList(adapter, notifList)

        binding.listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String

            for (notifOpen in notifList){
                if(notifOpen.notifText==selectedItem){
                    val fragment = NotifDiscrFragment()
                    val bundle = Bundle()
                    bundle.putParcelable("notif", notifOpen)
                    fragment.arguments = bundle
                    navigator().showItemInShopFragment(fragment)
                }
            }


        }



            return binding.root
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbRef = FirebaseDatabase.getInstance().getReference("Notification")

        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, arrayListOf())
        binding.listView.adapter = adapter

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = ArrayList<String>()

                for (childSnapshot in snapshot.children) {
                    val text = childSnapshot.child("notifText").getValue(String::class.java)
                    text?.let {
                        items.add(it)
                    }
                }

                adapter.clear()
                adapter.addAll(items)
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }*/




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
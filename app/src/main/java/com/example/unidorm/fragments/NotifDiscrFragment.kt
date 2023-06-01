package com.example.unidorm.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.unidorm.databinding.FragmentAccountBinding
import com.example.unidorm.databinding.FragmentNotifDiscrBinding
import com.example.unidorm.model.dbModel.NotificationModel
import com.example.unidorm.model.dbModel.ShopItemModel
import com.example.unidorm.model.dbModel.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class NotifDiscrFragment : Fragment() {

    private var _binding: FragmentNotifDiscrBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val notif = arguments?.getParcelable<NotificationModel>("notif")

        _binding = FragmentNotifDiscrBinding.inflate(inflater, container, false)

        //fetchDataFromFirebase()

        if (notif != null) {
            binding.notifTextView.text=notif.notifText
        }
        if (notif != null) {
            binding.disctiptonNotifTextView.text=notif.discription
        }


        return binding.root
    }

    fun fetchDataFromFirebase() {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("Notification")


        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childSnapshot in dataSnapshot.children) {
                    val myData = childSnapshot.getValue(NotificationModel::class.java)

                    if (myData != null) {
                        binding.notifTextView.text = myData.notifText
                    }
                    if (myData != null) {
                        binding.disctiptonNotifTextView.text = myData.discription
                    }



                    }
                }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}
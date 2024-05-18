package com.example.unidorm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unidorm.databinding.FragmentAccountBinding
import com.example.unidorm.model.dbModel.ShopItemModel
import com.example.unidorm.model.dbModel.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList


class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbRef: DatabaseReference
    private var UserList = mutableListOf<UserModel>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAccountBinding.inflate(inflater, container, false)

        fetchDataFromFirebase()

        return binding.root
    }


    fun fetchDataFromFirebase() {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("UserData")

        val currentUser = FirebaseAuth.getInstance().currentUser
        val userUid = currentUser?.uid

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childSnapshot in dataSnapshot.children) {
                    val myData = childSnapshot.getValue(UserModel::class.java)
                    myData?.let {
                        if(myData.UserId==userUid){
                            binding.userRoomText.text = myData.Room
                            binding.userNameText.text = myData.Name
                            binding.userPhoneText.text = myData.Phone
                            binding.userDormNomText.text = myData.Dorm

                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}
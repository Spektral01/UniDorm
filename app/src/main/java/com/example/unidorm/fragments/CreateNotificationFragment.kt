package com.example.unidorm.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.unidorm.databinding.FragmentCreateNotificationBinding
import com.example.unidorm.model.dbModel.DBHandler
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage


class CreateNotificationFragment : Fragment() {

    private var _binding: FragmentCreateNotificationBinding? = null
    private val binding get() = _binding!!

   // private lateinit var editTextTextPersonName:EditText

    private lateinit var dbRef:DatabaseReference
    private var dbHandl = DBHandler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateNotificationBinding.inflate(inflater, container, false)


        binding.btnPublishNotif.setOnClickListener{publishNotification()}
//////////////////////////////
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("images/0685f0f7-b6ee-430e-9732-156efece0d85.jpg") // Replace "images/image.jpg" with your image file path

// Get the download URL for the image
        /*imageRef.downloadUrl.addOnSuccessListener { uri ->
            val imageUrl = uri.toString()
            // Use the imageUrl as needed (e.g., load it into an ImageView)

            // Example: Load the image into an ImageView using Glide
            Glide.with(this)
                .load(imageUrl)
                .into(binding.imageView4)
        }.addOnFailureListener { exception ->
            // Handle any errors that occur during the download URL retrieval
            Log.e("Firebase", "Error getting download URL: ${exception.message}")
        }*/
//////////////////


        return binding.root
    }

    fun publishNotification(){
        dbHandl.publishNotification(binding.ediTextNotif.text.toString(), binding.editTextNotifDiscr.text.toString())

    }



}
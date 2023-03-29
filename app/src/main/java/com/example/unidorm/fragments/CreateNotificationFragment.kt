package com.example.unidorm.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.unidorm.databinding.FragmentCreateNotificationBinding
import com.example.unidorm.dbModel.NotificationModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class CreateNotificationFragment : Fragment() {

    private var _binding: FragmentCreateNotificationBinding? = null
    private val binding get() = _binding!!

   // private lateinit var editTextTextPersonName:EditText

    private lateinit var dbRef:DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateNotificationBinding.inflate(inflater, container, false)

        dbRef = FirebaseDatabase.getInstance().getReference("Notification")
        binding.btnPublishNotif.setOnClickListener{publishNotification()}

        return binding.root
    }

    private fun publishNotification(){
        val notifText = binding.editTextTextPersonName.text.toString()
        if (notifText.isEmpty()) {
            binding.editTextTextPersonName.error = "Please enter notif"
        }
        val notifId = dbRef.push().key!!
        val notif = NotificationModel(notifId,notifText)
        dbRef.child(notifId).setValue(notif)
            .addOnCompleteListener{
                Toast.makeText(activity, "Data inserted successfully", Toast.LENGTH_LONG).show()
                binding.editTextTextPersonName.text.clear()

            }.addOnFailureListener { err ->
                Toast.makeText(activity, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}
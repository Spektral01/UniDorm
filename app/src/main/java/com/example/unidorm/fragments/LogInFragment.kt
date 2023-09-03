package com.example.unidorm.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.unidorm.MainActivity
import com.example.unidorm.NavigationActivity
import com.example.unidorm.R
import com.example.unidorm.databinding.FragmentCreateNotificationBinding
import com.example.unidorm.databinding.FragmentLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase

class LogInFragment : Fragment() {

    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    // private lateinit var editTextTextPersonName:EditText

    private lateinit var dbRef: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)

        //dbRef = FirebaseDatabase.getInstance().getReference("Notification")
        binding.loginBtn.setOnClickListener{onClickLogin()}

        auth = Firebase.auth

        return binding.root
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }
    }

    private fun onClickLogin() {
        val email = binding.editTextTextEmailAddress.text.toString()
        val password = binding.editTextTextPassword.text.toString()
        UserValidation(email, password){ isSuccessText ->
            if (isSuccessText) {
                FirebaseAuthentication(email, password){ isSuccessLog ->
                    if(isSuccessLog){
                        updateUI()
                    }
                    else{
                        Toast.makeText(activity, "Not correct", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(activity, "Not correct", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun UserValidation(email: String, pass: String, callback: (isSuccess: Boolean) -> Unit){
        if (email.isNotEmpty() && pass.isNotEmpty()) {
            callback(true)
        }
        else {
            callback(false)
        }
    }
    private fun FirebaseAuthentication(email: String, pass: String, callback: (isSuccess: Boolean) -> Unit){
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    callback(true)
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    callback(false)
                }
            }
    }

    private fun updateUI() {
        val intent = Intent(activity, NavigationActivity::class.java)
        //Toast.makeText(this, "WORK", Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }


    companion object {
        private const val TAG = "EmailPassword"
    }

    private fun reload() {
    }


}
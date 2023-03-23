package com.example.unidorm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.unidorm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.guestBtn.setOnClickListener { onClickGuestLogin() }
        binding.loginBtn.setOnClickListener { onClickUserLogin() }
    }

    private fun onClickGuestLogin() {
        val intent = Intent(this, NotificationActivity::class.java)
        Toast.makeText(this, "WORK",Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }
    private fun onClickUserLogin() {

        Toast.makeText(this, "WORK",Toast.LENGTH_SHORT).show()

    }



}
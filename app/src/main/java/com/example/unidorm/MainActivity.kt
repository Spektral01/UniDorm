package com.example.unidorm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.unidorm.databinding.ActivityMainBinding
import com.example.unidorm.fragments.AddressInfoFragment
import com.example.unidorm.fragments.LogInFragment
import com.example.unidorm.fragments.contract.Navigator
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.guestBtn.setOnClickListener { onClickGuestLogin() }
        binding.loginBtn.setOnClickListener { onClickUserLogin() }

        MapKitFactory.setApiKey("ee9a0510-7f4e-4973-8242-c9cafa9715af")
    }

    fun launchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun onClickGuestLogin() {
        val intent = Intent(this, NavigationActivity::class.java)
        Toast.makeText(this, "WORK", Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }

    private fun onClickUserLogin() {

        val constraintLayout = findViewById<ConstraintLayout>(R.id.constraint_main)

        for (i in 0 until constraintLayout.childCount) {
            val view = constraintLayout.getChildAt(i)
            if (view !is FrameLayout) {
                view.visibility = View.GONE
            } else view.visibility = View.VISIBLE

        }
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, LogInFragment())
            .commit()
    }

    override fun onBackPressed() {
        val constraintLayout = findViewById<ConstraintLayout>(R.id.constraint_main)
        val framelayo = findViewById<FrameLayout>(R.id.fragment_container)
        for (i in 0 until constraintLayout.childCount) {
            val view = constraintLayout.getChildAt(i)
            if (view !is FrameLayout) {
                view.visibility = View.VISIBLE
            }
        }
        framelayo.visibility = View.GONE
    }

}
package com.example.unidorm

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.unidorm.databinding.ActivityNavigationBinding
import com.example.unidorm.fragments.AddressInfoFragment
import com.example.unidorm.fragments.CreateNotificationFragment
import com.example.unidorm.fragments.NotificationFragment
import com.example.unidorm.fragments.contract.Navigator


class NavigationActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityNavigationBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.apply {
            toggle = ActionBarDrawerToggle(this@NavigationActivity, drawerLayout, R.string.open, R.string.close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navView.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.nav_notification -> showNotificationScreen()
                    R.id.nav_info -> showInfoScreen()
                    R.id.nav_map -> showCreateNotificationScreen()
                }
                true
            }
        }


        supportFragmentManager.beginTransaction()
            .add(binding.fragmentContainer.id, NotificationFragment())
            .commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
      if(toggle.onOptionsItemSelected(item)){
          true
      }
        return super.onOptionsItemSelected(item)
    }



    fun launchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun showInfoScreen() {
        launchFragment(AddressInfoFragment())
    }

    override fun showNotificationScreen() {
        launchFragment(NotificationFragment())
    }

    override fun showCreateNotificationScreen() {
        launchFragment(CreateNotificationFragment())
    }



}
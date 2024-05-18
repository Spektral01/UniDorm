package com.example.unidorm

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.unidorm.databinding.ActivityNavigationBinding
import com.example.unidorm.fragments.AccountFragment
import com.example.unidorm.fragments.AddressInfoFragment
import com.example.unidorm.fragments.CreateNotificationFragment
import com.example.unidorm.fragments.ItemSellFragment
import com.example.unidorm.fragments.NotificationFragment
import com.example.unidorm.fragments.SearchItemFragment
import com.example.unidorm.fragments.ShopFragment
import com.example.unidorm.fragments.contract.Navigator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class NavigationActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityNavigationBinding
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater).also { setContentView(it.root) }

        auth = Firebase.auth

        binding.apply {
            toggle = ActionBarDrawerToggle(this@NavigationActivity, drawerLayout, R.string.open, R.string.close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            binding.logout.setOnClickListener{
                Firebase.auth.signOut()
                drawerLayout.closeDrawer(GravityCompat.START)

                val intent = Intent(this@NavigationActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                //Toast.makeText(activity, "WORK", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
            navView.setNavigationItemSelectedListener {
                drawerLayout.closeDrawer(GravityCompat.START)

                when(it.itemId){
                    R.id.nav_notification -> showNotificationScreen()
                    R.id.nav_info -> showInfoScreen()
                    R.id.nav_shop -> showShopScreen()
                    R.id.nav_map -> showMapScreen()
                    R.id.nav_account -> showAccountScreen()
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

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun showInfoScreen() {
        launchFragment(AddressInfoFragment())
    }
    override fun showShopScreen() {
        launchFragment(ShopFragment())
    }
    override fun showNotificationScreen() {
        launchFragment(NotificationFragment())
    }

    override fun showItemSellScreen() {
        launchFragment(ItemSellFragment())
    }

    override fun showAccountScreen() {
        launchFragment(AccountFragment())
    }

    override fun showCreateNotificationScreen() {
        launchFragment(CreateNotificationFragment())
    }
    override fun showSearchItemDrawer()
    {
        launchFragment(SearchItemFragment())
    }

    override fun showItemInShopFragment(fragment: Fragment){
        launchFragment(fragment)
    }

    override fun showMapScreen(){
        launchFragment(MapsFragment())
    }
}
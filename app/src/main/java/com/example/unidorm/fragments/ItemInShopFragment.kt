package com.example.unidorm.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.unidorm.databinding.FragmentCreateNotificationBinding
import com.example.unidorm.databinding.FragmentItemInShopBinding
import com.example.unidorm.model.dbModel.DBHandler
import com.example.unidorm.model.dbModel.DataListener
import com.example.unidorm.model.dbModel.ShopItemModel
import com.google.firebase.storage.FirebaseStorage

class ItemInShopFragment : Fragment() {

    private var _binding: FragmentItemInShopBinding? = null
    private val binding get() = _binding!!

    val dbHandl = DBHandler()
        //private lateinit var item: ShopItemModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemInShopBinding.inflate(inflater, container, false)

        val item = arguments?.getParcelable<ShopItemModel>("item")

        if (item != null) {
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("images/" + item.Picture + ".jpg") // Replace "images/image.jpg" with your image file path

            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                // Загрузка изображения из Firebase Storage с помощью библиотеки Glide
                Glide.with(this)
                    .load(imageUrl)
                    .into(binding.imageItem)
            }.addOnFailureListener { exception ->
                Log.e("Firebase", "Error getting download URL: ${exception.message}")
            }
            binding.textItem.text = item.Item
            binding.textDiscr.text = item.Description
            binding.textPerson.text = item.PersonName
            binding.textPhone.text = item.PersonNumber
            binding.textPrice.text = item.Price


        }

        return binding.root
    }



}
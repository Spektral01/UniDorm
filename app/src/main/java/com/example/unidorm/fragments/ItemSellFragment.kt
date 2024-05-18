package com.example.unidorm.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.unidorm.databinding.FragmentItemSellBinding
import com.example.unidorm.model.dbModel.DBHandler
import com.example.unidorm.model.dbModel.ShopItemModel
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore

class ItemSellFragment : Fragment() {

    companion object {
        fun newInstance() = ItemSellFragment()
        private const val PICK_IMAGE_REQUEST = 123
    }

    private lateinit var viewModel: ItemSellViewModel

    private lateinit var binding: FragmentItemSellBinding

    private var dbHandler = DBHandler()

    private lateinit var item : ShopItemModel
    private lateinit var selectedImageUrl: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemSellBinding.inflate(inflater, container, false)

        tempSetPic()

        binding.imageButton.setOnClickListener{
            selectImageFromGallery()
        }

        binding.btnSellItem.setOnClickListener{

            item = ShopItemModel(
                null,
                binding.editTextNameItem.text.toString(),
                binding.editTextPriceItem.text.toString(),
                binding.editTextPersonNameWhoSell.text.toString(),
                binding.editTextPhoneNumPersonSell.text.toString(),
                binding.editTextDescriptionItem.text.toString(),
                null
            )

            dbHandler.publishItem(item)

            Toast.makeText(requireContext(), "WORK", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ItemSellViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun tempSetPic()
    {
        val imageUrls = listOf(
            "https://images.unsplash.com/photo-1681867207129-a26a1f6e8346?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3wxODY2Nzh8MHwxfHJhbmRvbXx8fHx8fHx8fDE2ODQ0MTMzMzl8&ixlib=rb-4.0.3&q=80&w=1080",
            "https://images.unsplash.com/photo-1684316336336-6ecacd9b7a18?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3wxODY2Nzh8MHwxfHJhbmRvbXx8fHx8fHx8fDE2ODQ0MTI5OTB8&ixlib=rb-4.0.3&q=80&w=1080",
            "https://images.unsplash.com/photo-1681867207129-a26a1f6e8346?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3wxODY2Nzh8MHwxfHJhbmRvbXx8fHx8fHx8fDE2ODQ0MTMzMzl8&ixlib=rb-4.0.3&q=80&w=1080"

        )
        for (imageUrl in imageUrls) {

            val imageView = ImageView(requireContext())

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )

            layoutParams.weight = 1f

            imageView.layoutParams = layoutParams

            Glide.with(this).load(imageUrl).into(imageView)

            binding.picLinearLayout.addView(imageView)
        }

    }

    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            selectedImageUri?.let {
                // Передаем выбранное изображение для загрузки в Firebase Storage
                dbHandler.uploadImageToFirebaseStorage(requireContext(), it)
            }
        }
    }

}
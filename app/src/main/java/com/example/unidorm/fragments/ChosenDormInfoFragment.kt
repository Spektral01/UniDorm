package com.example.unidorm.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.unidorm.databinding.FragmentAddressInfoBinding
import com.example.unidorm.databinding.FragmentChosenDormInfoBinding
import com.example.unidorm.databinding.FragmentNotifDiscrBinding
import com.example.unidorm.model.dbModel.DormitoryModel
import com.example.unidorm.model.dbModel.NotificationModel


class ChosenDormInfoFragment : Fragment() {

    private var _binding: FragmentChosenDormInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChosenDormInfoBinding.inflate(inflater, container, false)

        val dorm = arguments?.getParcelable<DormitoryModel>("dorm")

        _binding = FragmentChosenDormInfoBinding.inflate(inflater, container, false)

        //fetchDataFromFirebase()

        if (dorm != null) {
            binding.textCastelTime.text=dorm.castellanTime
            binding.dormName.text=dorm.dormName
            binding.textShower.text=dorm.showerTime
            binding.addressText.text=dorm.dormAddress
            binding.textPassTime.text=dorm.passportTime
            binding.textCommTime.text=dorm.commendTime
            dorm.dormPhoto?.let { showPicture(it) }

        }




        return binding.root
    }

    private fun showPicture(picURL:String){
        Glide.with(this)
            .load(picURL)
            .into(binding.imageDorm)
    }

}
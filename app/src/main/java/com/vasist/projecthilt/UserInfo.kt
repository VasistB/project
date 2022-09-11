package com.vasist.projecthilt

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isGone
import coil.load
import com.vasist.projecthilt.databinding.ActivityUserInfoBinding
import com.vasist.projecthilt.model.Result

class UserInfo : AppCompatActivity() {
    lateinit var binding: ActivityUserInfoBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val user= intent.getSerializableExtra("position") as Result

        binding.detailUserName.text=user.name.title +" "+ user.name.first+ " "+user.name.last
        binding.detailUserPhone.text=user.phone
        binding.detailUserEmail.text=user.email
        binding.detailUserGender.text=user.gender
        binding.State.text=user.location.state
        binding.Street.text=user.location.street.name
        binding.city.text=user.location.city
        binding.Country.text=user.location.country
        binding.detailPicture.load(user.picture.large){
            listener { request, result ->
                binding.progress.isGone=true
            }
        }
    }
}
package com.CH2PS073.diabetless.ui.main.bottomSheetMenu.profile

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.CH2PS073.diabetless.data.remote.response.DetailUser
import com.CH2PS073.diabetless.databinding.ActivityProfileSettingBinding
import com.CH2PS073.diabetless.ui.ViewModelFactory
import com.CH2PS073.diabetless.ui.main.MainViewModel
import com.bumptech.glide.Glide

class ProfileSettingActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val viewModel by viewModels<ProfileSettingViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding : ActivityProfileSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mainViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                viewModel.getDetailUser(user.token)
            }
        }

        viewModel.users.observe(this) { detail ->
            setDetailUser(detail)
        }

        Glide.with(this@ProfileSettingActivity)
            .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRnNrVNnF6Jv3Ft4Ev80D8phS_Jlz052qBffBQkRRKeojzqK0NU")
            .circleCrop()
            .into(binding.profileImage)

        binding.changePasswordButton.setOnClickListener {
            showToast("fitur change password belum tersedia")
        }

        binding.saveChangeButton.setOnClickListener {
            showToast("fitur save change belum tersedia")
        }

    }

    private fun setDetailUser(detail: DetailUser) {
        binding.nameEditText.setText(detail.fullName)
        binding.emailEditText.setText(detail.email)
        binding.usernameEditText.setText(detail.username)
        Glide.with(this@ProfileSettingActivity)
            .load(detail.profilePicture)
            .circleCrop()
            .into(binding.profileImage)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
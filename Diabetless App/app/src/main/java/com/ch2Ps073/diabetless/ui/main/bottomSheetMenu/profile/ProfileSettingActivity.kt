package com.ch2Ps073.diabetless.ui.main.bottomSheetMenu.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ch2Ps073.diabetless.R
import com.ch2Ps073.diabetless.data.remote.response.DetailUser
import com.ch2Ps073.diabetless.data.remote.response.RegisterResponse
import com.ch2Ps073.diabetless.databinding.ActivityProfileSettingBinding
import com.ch2Ps073.diabetless.ui.ViewModelFactory
import com.ch2Ps073.diabetless.ui.main.MainViewModel
import com.ch2Ps073.diabetless.ui.main.ui.home.HomeFragment
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

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

        binding.nameEditText.setText(getString(R.string.fullnameUlin))

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

        lifecycleScope.launch {
            try {
                AlertDialog.Builder(this@ProfileSettingActivity).apply {
                    setTitle("Email dan username")
                    setMessage("email anda :\"${detail.email}\" \nusername anda : \"${detail.username}\"\n")
                    setPositiveButton("Lanjut") { _, _ ->
                        startActivity(Intent(this@ProfileSettingActivity, HomeFragment::class.java))
                    }
                    create()
                    show()
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
                showToast(errorResponse.message)
            }
        }

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
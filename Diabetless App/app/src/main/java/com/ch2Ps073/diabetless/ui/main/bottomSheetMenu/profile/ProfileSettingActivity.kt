package com.ch2Ps073.diabetless.ui.main.bottomSheetMenu.profile

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ch2Ps073.diabetless.data.remote.response.DetailUser
import com.ch2Ps073.diabetless.databinding.ActivityProfileSettingBinding
import com.ch2Ps073.diabetless.ui.ViewModelFactory
import com.ch2Ps073.diabetless.ui.main.MainViewModel
import com.ch2Ps073.diabetless.ui.main.bottomSheetMenu.profile.changePassword.ChangePasswordActivity
import com.ch2Ps073.diabetless.utils.reduceFileImage
import com.ch2Ps073.diabetless.utils.uriToFile
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ProfileSettingActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val viewModel by viewModels<ProfileSettingViewModel> {
        ProfileViewModelFactory.getInstance(this, lifecycleScope)
    }

    private lateinit var binding: ActivityProfileSettingBinding
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.profileImage.setOnClickListener { startGallery() }

        binding.birthdayEditText.setOnClickListener {
            showDatePickerDialog()
        }

        mainViewModel.getSession().observe(this) { user ->
            viewModel.getDetailUser(user.token)
            viewModel.users.observe(this) { detail ->
                setDetailUser(detail)
            }

            binding.saveChangeButton.setOnClickListener {
                val name = binding.nameEditText.text.toString()
                val email = binding.emailEditText.text.toString()
                val username = binding.usernameEditText.text.toString()
                val dateBirthday = binding.birthdayEditText.text.toString()

                currentImageUri?.let { uri ->
                    val imageFile = uriToFile(uri, this).reduceFileImage()
                    viewModel.editProfile(user.token, name, email, imageFile, username, dateBirthday)
                }
            }
        }

        binding.changePasswordButton.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()

        // Create a DatePickerDialog and set the listener
        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                // Format the selected date and set it to the text field
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                binding.birthdayEditText.setText(dateFormat.format(selectedDate.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Show the DatePickerDialog
        datePickerDialog.show()
    }

    private fun setDetailUser(detail: DetailUser) {
        binding.nameEditText.setText(detail.fullName)
        binding.emailEditText.setText(detail.email)
        binding.usernameEditText.setText(detail.username)
        binding.birthdayEditText.setText(detail.birthday)
        if (detail.profilePicture == "") {
            Glide.with(this@ProfileSettingActivity)
                .load("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png")
                .circleCrop()
                .into(binding.profileImage)
        } else {
            Glide.with(this@ProfileSettingActivity)
                .load(detail.profilePicture.toString())
                .circleCrop()
                .into(binding.profileImage)
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            showToast("No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let { uri ->
            Glide.with(this@ProfileSettingActivity)
                .load(uri)
                .circleCrop()
                .into(binding.profileImage)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

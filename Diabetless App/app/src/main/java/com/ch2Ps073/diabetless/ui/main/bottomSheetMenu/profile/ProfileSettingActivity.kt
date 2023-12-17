package com.ch2Ps073.diabetless.ui.main.bottomSheetMenu.profile

<<<<<<< HEAD
import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
=======
>>>>>>> chello
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
<<<<<<< HEAD
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
=======
>>>>>>> chello
import com.bumptech.glide.Glide
import com.ch2Ps073.diabetless.R
import com.ch2Ps073.diabetless.data.remote.response.DetailUser
import com.ch2Ps073.diabetless.databinding.ActivityProfileSettingBinding
import com.ch2Ps073.diabetless.ui.ViewModelFactory
import com.ch2Ps073.diabetless.ui.main.MainViewModel
<<<<<<< HEAD
import com.ch2Ps073.diabetless.ui.main.bottomSheetMenu.profile.changePassword.ChangePasswordFragment
import com.ch2Ps073.diabetless.utils.getImageUri
import com.ch2Ps073.diabetless.utils.reduceFileImage
import com.ch2Ps073.diabetless.utils.uriToFile
=======
>>>>>>> chello

class ProfileSettingActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val viewModel by viewModels<ProfileSettingViewModel> {
        ProfileViewModelFactory.getInstance(this, lifecycleScope)
    }

    private lateinit var binding : ActivityProfileSettingBinding

    private var currentImageUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.profileImage.setOnClickListener { startGallery() }

        mainViewModel.getSession().observe(this) { user ->
            viewModel.getDetailUser(user.token)

            binding.saveChangeButton.setOnClickListener {
                val name = binding.nameEditText.text.toString()
                val email = binding.emailEditText.text.toString()
                val username = binding.usernameEditText.text.toString()

                currentImageUri?.let { uri ->
                    val imageFile = uriToFile(uri, this).reduceFileImage()
                    Log.d("Image File", "showImage: ${imageFile.path}")

                    viewModel.editProfile(user.token, name, email, imageFile, username)

                }
            }
        }

        viewModel.users.observe(this) { detail ->
            setDetailUser(detail)
        }

        binding.changePasswordButton.setOnClickListener {
            val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.profileActivity, ChangePasswordFragment())
            fragmentTransaction.disallowAddToBackStack()
            fragmentTransaction.commit()
        }

    }

    private fun setDetailUser(detail: DetailUser) {
        binding.nameEditText.setText(detail.fullName)
        binding.emailEditText.setText(detail.email)
        binding.usernameEditText.setText(detail.username)
        if (detail.profilePicture == ""){
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

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage() //untuk menampilkan
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.profileImage.setImageURI(it)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }

}
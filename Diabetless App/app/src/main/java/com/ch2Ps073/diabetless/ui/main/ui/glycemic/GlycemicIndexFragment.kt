package com.ch2Ps073.diabetless.ui.main.ui.glycemic

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.ch2Ps073.diabetless.R
import com.ch2Ps073.diabetless.databinding.FragmentGlycemicIndexBinding
import com.ch2Ps073.diabetless.ui.main.MainActivity
import com.ch2Ps073.diabetless.ui.main.ui.home.HomeFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

class GlycemicIndexFragment : Fragment() {

    private var imageCapture: ImageCapture? = null
    private var cameraPosition: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val image = result.data?.data as Uri
            image.let { uri ->
            }
            showGlycemixBottomMenu()
        }
    }

    private var _binding: FragmentGlycemicIndexBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGlycemicIndexBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity?)?.supportActionBar?.hide()

        startCameraService()
        setListeners()

        return root
    }

    private fun setListeners() {
        binding.apply {
            topAppBar.setNavigationOnClickListener {
                val transaction = activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.nav_host_fragment_activity_main, HomeFragment())
                transaction?.disallowAddToBackStack()
                transaction?.commit()
                val activity = requireActivity() as MainActivity
                activity.binding.navView.isVisible = true
                activity.binding.navView.selectedItemId = R.id.navigation_home
            }

            btnShutter.setOnClickListener {
                takePhoto()
            }

            btnGallery.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "image/*"
                val chooser = Intent.createChooser(intent, "Choose a Picture")
                galleryLauncher.launch(chooser)
            }
        }
    }

    private fun startCameraService() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(Size(720, 1280))
                .build()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder().setTargetResolution(Size(720, 1280)).build()
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraPosition,
                    preview,
                    imageCapture, imageAnalysis
                )
            } catch (e: Exception) {
                showToast("Error : ${e.message}")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }


    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val capturedImageFile = createFile(requireActivity().application)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(capturedImageFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(e: ImageCaptureException) {
                    showToast("Error : ${e.message}")
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    showGlycemixBottomMenu()
                }
            }
        )
    }

    private fun showGlycemixBottomMenu() {
        val glycemixBottomMenu = BottomSheetDialog(requireActivity(), R.style.SheetDialog);
        glycemixBottomMenu.setContentView(R.layout.bottom_sheet_glycemix_camera_layout)

        glycemixBottomMenu.show()
    }

    private fun createFile(application: Application): File {
        val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
            File(it, "image").apply { mkdirs() }
        }

        return File(
            if (
                mediaDir != null && mediaDir.exists()
            ) mediaDir else application.filesDir, "${
                SimpleDateFormat(
                    "ddMMyySSSSS",
                    Locale.getDefault()
                ).format(System.currentTimeMillis())
            }.jpg"
        )
    }

    companion object {
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_CAMERA_MODE = "extra_camera_mode"
    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }
}
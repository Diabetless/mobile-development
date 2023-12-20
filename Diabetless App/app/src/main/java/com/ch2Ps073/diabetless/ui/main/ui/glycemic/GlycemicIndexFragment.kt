package com.ch2Ps073.diabetless.ui.main.ui.glycemic

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Camera
import androidx.camera.core.CameraInfoUnavailableException
import androidx.camera.core.CameraSelector
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.MeteringPointFactory
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceOrientedMeteringPointFactory
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ch2Ps073.diabetless.R
import com.ch2Ps073.diabetless.databinding.FragmentGlycemicIndexBinding
import com.ch2Ps073.diabetless.ui.main.MainActivity
import com.ch2Ps073.diabetless.ui.main.ui.home.HomeFragment
import com.ch2Ps073.diabetless.utils.ContentUriUtil
import com.ch2Ps073.diabetless.utils.Result
import com.ch2Ps073.diabetless.utils.getBitmapFromUri
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class GlycemicIndexFragment : Fragment() {

    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var cameraPosition: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private var camera: Camera? = null
    private val cameraExecutor = Executors.newSingleThreadExecutor()

    private val recommendationsMealDialog: RecommendationMealDialog by lazy {
        RecommendationMealDialog(requireContext())
    }
    private val mealsDialog: MealsDetailsDialog by lazy {
        MealsDetailsDialog(requireContext(), { d ->
            //startCameraService()
            d.dismiss()
        }) { recommendationsMeal ->
            recommendationsMealDialog.setRecommendationItem(recommendationsMeal)
            recommendationsMealDialog.showDialog()
        }
    }


    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val image = result.data?.data
            image?.apply {
                val capturedBitmap = getBitmapFromUri(requireContext(), this)
                val imageFile = ContentUriUtil.getFilePath(requireContext(), this)
                if (imageFile != null) {
                    viewModel.detectMealNutrition(File(imageFile), capturedBitmap!!)
                } else {
                    showToast("Please grant storage permission to use this feature")
                }
            }
        }
    }


    private var _binding: FragmentGlycemicIndexBinding? = null
    private val binding get() = _binding!!

    private val requestRequiredPermission by lazy {
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { isGranted ->
            if (isGranted.containsValue(false)) {
                showToast("Please grant all permission to use this feature")
            } else {
                startCameraService()
            }
        }
    }

    val viewModel by viewModels<GlycemicViewModel> {
        GlycemicViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGlycemicIndexBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity?)?.supportActionBar?.hide()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        observeState()

        if (!checkImagePermission()) {
            askPermission()
        } else {
            startCameraService()
        }
    }

    private fun setListeners() {
        binding.apply {
            topAppBar.setNavigationOnClickListener {
                /*val transaction = activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.nav_host_fragment_activity_main, HomeFragment())
                transaction?.disallowAddToBackStack()
                transaction?.commit()

                val activity = requireActivity() as MainActivity
                activity.binding.navView.isVisible = true
                activity.binding.navView.selectedItemId = R.id.navigation_home*/
                findNavController().popBackStack()
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

    @SuppressLint("UnsafeOptInUsageError", "ClickableViewAccessibility")
    private fun startCameraService() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val resolutionSelectorBuilder = ResolutionSelector.Builder()
                .apply {
                    /* setResolutionStrategy(
                         ResolutionStrategy(
                             Size(
                                 224,
                                 224
                             ),
                             ResolutionStrategy.FALLBACK_RULE_CLOSEST_HIGHER_THEN_LOWER
                         )
                     )*/
                }
                .build()

            val imageAnalysis = ImageAnalysis.Builder()
                .setResolutionSelector(resolutionSelectorBuilder)
                .build()

            preview = Preview.Builder()
                .setResolutionSelector(resolutionSelectorBuilder)
                .build()

            imageCapture = ImageCapture.Builder()
                .setResolutionSelector(resolutionSelectorBuilder)
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                .build()

            try {
                cameraProvider.unbindAll()
                camera = cameraProvider.bindToLifecycle(
                    this,
                    cameraPosition,
                    preview,
                    imageCapture,
                    imageAnalysis
                )

                binding.viewFinder.afterMeasured {
                    val autoFocusPoint = SurfaceOrientedMeteringPointFactory(1f, 1f)
                        .createPoint(.5f, .5f)
                    try {
                        val autoFocusAction = FocusMeteringAction.Builder(
                            autoFocusPoint,
                            FocusMeteringAction.FLAG_AF
                        ).apply {
                            //start auto-focusing after 2 seconds
                            setAutoCancelDuration(30, TimeUnit.MILLISECONDS)
                        }.build()
                        camera?.cameraControl?.startFocusAndMetering(autoFocusAction)
                    } catch (e: CameraInfoUnavailableException) {
                        Log.d("ERROR", "cannot access camera", e)
                    }
                }

                preview?.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            } catch (e: Exception) {
                showToast("Error : ${e.message}")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePhoto() {
        binding.progressCircular.visibility = View.VISIBLE

        val imageCapture = imageCapture ?: return
        val capturedImageFile = createFile(requireActivity().application)

        val metadata = ImageCapture.Metadata()
        val outputOptions = ImageCapture.OutputFileOptions.Builder(capturedImageFile)
            .setMetadata(metadata)
            .build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(e: ImageCaptureException) {
                    showToast("Error : ${e.message}")
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val capturedBitmap = getBitmapFromUri(requireContext(), output.savedUri)

                    // cropped image with rect overlay frame
                    /* run {
                         val imageBytes =
                             cropImage(
                                 capturedBitmap!!,
                                 binding.viewFinder,
                                 binding.capturedFrame
                             )

                         val croppedImage =
                             BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

                         try {
                             val outputStream = FileOutputStream(capturedImageFile)
                             croppedImage.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                         } catch (e: Exception) {
                             e.printStackTrace()
                         }
                     }*/


                    viewModel.detectMealNutrition(capturedImageFile, capturedBitmap!!)
                    // stop preview when image capture
                    // preview?.setSurfaceProvider(null)
                }
            }
        )
    }

    private fun createFile(application: Application): File {
        val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
            File(it, "image").apply { mkdirs() }
        }

        val file = File(
            if (
                mediaDir != null && mediaDir.exists()
            ) mediaDir else application.filesDir, "${
                SimpleDateFormat(
                    "ddMMyySSSSS",
                    Locale.getDefault()
                ).format(System.currentTimeMillis())
            }.jpg"
        )

        Log.d("Captured Image", "image file created ${file.absolutePath}")

        return file
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

    private fun checkImagePermission() = MainActivity.REQUIRED_REQUIRED_PERMISSION.all {
        ContextCompat.checkSelfPermission(
            requireContext(),
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun observeState() {
        viewModel.detectedMeal.observe(viewLifecycleOwner) { state ->
            if (state is Result.Success) {
                binding.progressCircular.visibility = View.GONE
                mealsDialog.setMealItems(state.data.first?.toMutableList(), state.data.second)
                mealsDialog.showDialog()

                showToast("Object detected")
            } else if (state is Result.Error) {
                binding.progressCircular.visibility = View.GONE
                showToast(state.error)
            } else {
                binding.progressCircular.visibility = View.VISIBLE
            }
        }
    }

    private fun askPermission() {
        requestRequiredPermission.launch(MainActivity.REQUIRED_REQUIRED_PERMISSION)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun autoFocusByClick() {
        binding.viewFinder.afterMeasured {
            binding.viewFinder.setOnTouchListener { _, event ->
                return@setOnTouchListener when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        true
                    }

                    MotionEvent.ACTION_UP -> {
                        val factory: MeteringPointFactory = SurfaceOrientedMeteringPointFactory(
                            binding.viewFinder.width.toFloat(),
                            binding.viewFinder.height.toFloat()
                        )
                        val autoFocusPoint = factory.createPoint(event.x, event.y)
                        try {
                            camera?.cameraControl?.startFocusAndMetering(
                                FocusMeteringAction.Builder(
                                    autoFocusPoint,
                                    FocusMeteringAction.FLAG_AF
                                ).apply {
                                    //focus only when the user tap the preview
                                    disableAutoCancel()
                                }.build()
                            )
                        } catch (e: CameraInfoUnavailableException) {
                            Log.d("ERROR", "cannot access camera", e)
                        }
                        true
                    }

                    else -> false // Unhandled event.
                }
            }
        }
    }

    private inline fun View.afterMeasured(crossinline block: () -> Unit) {
        if (measuredWidth > 0 && measuredHeight > 0) {
            block()
        } else {
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    if (measuredWidth > 0 && measuredHeight > 0) {
                        viewTreeObserver.removeOnGlobalLayoutListener(this)
                        block()
                    }
                }
            })
        }
    }
}
package com.blblblbl.myapplication.view

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.blblblbl.myapplication.R
import com.blblblbl.myapplication.databinding.FragmentCameraBinding
import com.blblblbl.myapplication.databinding.FragmentMainBinding
import com.blblblbl.myapplication.presentation.CameraViewModel
import com.blblblbl.myapplication.presentation.MainViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor


private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss"
@AndroidEntryPoint
class CameraFragment : Fragment() {


    private var imageCapture: ImageCapture? = null
    lateinit var binding: FragmentCameraBinding
    private val viewModel: CameraViewModel by viewModels()
    private lateinit var executor: Executor
    private val launcher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted->
        Toast.makeText(context,"camera permission is $isGranted", Toast.LENGTH_LONG).show()
        startCamera()
    }
    private val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraBinding.inflate(layoutInflater)
        binding.bShot.setOnClickListener{ takePhoto() }
        executor = context?.let { ContextCompat.getMainExecutor(it) }!!
        startCamera()
        return binding.root
    }


    private fun startCamera(){
        context?.let {
            val cameraProviderFuture= ProcessCameraProvider.getInstance(it)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build()
                preview.setSurfaceProvider(binding.preview.surfaceProvider)
                imageCapture = ImageCapture.Builder().build()
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageCapture
                )
            },executor)}
    }
    fun takePhoto(){
        val imageCapture = imageCapture?:return

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME,name)
            put(MediaStore.MediaColumns.MIME_TYPE,"image/jpeg")
        }

        val outputOptions = context?.let {
            ImageCapture.OutputFileOptions.Builder(
                it.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ).build()
        }

        imageCapture.takePicture(
            outputOptions!!,
            executor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    viewModel.savePhoto(outputFileResults.savedUri.toString(), name)
                    context?.let {
                        Glide.with(it)
                            .load(outputFileResults.savedUri)
                            .circleCrop()
                            .into(binding.ivPreview)
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    throw exception
                    Toast.makeText(context,"photo failed",Toast.LENGTH_SHORT).show()
                }
            })
    }


}
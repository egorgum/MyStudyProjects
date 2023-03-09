package com.example.practice_18.ui.second

import android.content.ContentResolver
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.ContentInfo
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.video.OutputFileResults
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practice_18.R
import com.example.practice_18.data.App
import com.example.practice_18.databinding.FragmentMainBinding
import com.example.practice_18.databinding.FragmentSecondBinding
import com.example.practice_18.ui.main.MainViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor
import java.util.jar.Manifest

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val FILE_NAME_FORMAT = "yyyy-MM-dd-HH-mm-ss"

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragment : Fragment() {
    private var imageCapture: ImageCapture? = null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SecondViewModel by viewModels{object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val photoDao = (requireActivity().application as App).db.dao()
            return SecondViewModel(photoDao) as T
        }
    }}

    private val launcher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){map->
        if (map.values.all{it}){
            showCamera()
        }
    }
    lateinit var executor: Executor

    private val name = SimpleDateFormat(FILE_NAME_FORMAT, Locale.US).format(System.currentTimeMillis())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        executor = ContextCompat.getMainExecutor(this.requireContext())

        checkPermission()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener{
            takePhoto()
        }
    }

    private fun takePhoto() {
        val imageCapture =imageCapture?: return
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME,name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        }
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(
            requireContext().contentResolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues).build()
        imageCapture.takePicture(
            outputFileOptions,
            executor,
            object: ImageCapture.OnImageSavedCallback{
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    viewModel.addButton(outputFileResults.savedUri.toString(),
                        name)
                }

                override fun onError(exception: ImageCaptureException) {
                    exception.printStackTrace()
                }
            }
        )

    }

    private fun showCamera(){
        val cameraProviderFuture =  ProcessCameraProvider.getInstance(this.requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(binding.camera.surfaceProvider)
            imageCapture = ImageCapture.Builder().build()
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                this,
                CameraSelector.DEFAULT_BACK_CAMERA,
                preview,
                imageCapture
            )
        },executor)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun checkPermission(){
        val allGranted = REQUEST_PERMISSIONS.all{permission -> ContextCompat.checkSelfPermission(this.requireContext(), permission)== PackageManager.PERMISSION_GRANTED}
        if (allGranted){
            showCamera()
        }
        else{
            launcher.launch(REQUEST_PERMISSIONS)
        }
    }

    companion object{
        private val REQUEST_PERMISSIONS: Array<String> = buildList {
            add(android.Manifest.permission.CAMERA)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P){
                add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()
    }
}

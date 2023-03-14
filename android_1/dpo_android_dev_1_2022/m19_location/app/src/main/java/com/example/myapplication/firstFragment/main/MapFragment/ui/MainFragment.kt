package com.example.myapplication.firstFragment.main.MapFragment.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMapsBinding
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        private val REQUIRED_PERMISSIONS: Array<String>  = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    private val viewModel: MainViewModel by viewModels{ Factory() }

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private val launcher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
        map ->
        if (map.values.isNotEmpty() && map.values.all { it }){
            viewModel.startLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        viewModel.map = googleMap
        with(viewModel.map!!.uiSettings) {
            this.isZoomControlsEnabled = true
            isMyLocationButtonEnabled = true
        }
        viewModel.map!!.setLocationSource(object : LocationSource{
            override fun activate(p0: LocationSource.OnLocationChangedListener) {
                viewModel.locationListener = p0
            }
            override fun deactivate() {
               viewModel.locationListener = null

            }
        })


    }

    private fun checkPermission(){
        if(REQUIRED_PERMISSIONS.all { permission ->
                ContextCompat.checkSelfPermission(this.requireContext(),permission) == PackageManager.PERMISSION_GRANTED
        })
            viewModel.startLocation()
        else launcher.launch(REQUIRED_PERMISSIONS)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fusedClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())
        checkPermission()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(
            callback)
        binding.mapOverlay.setOnTouchListener{v, event  ->
            viewModel.handler.removeCallbacks(viewModel.cameraMoveRunnable)
            viewModel.needMoveCamera = false
            viewModel.handler.postDelayed(viewModel.cameraMoveRunnable, 5_000)
            false
        }
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.genMarkers()
            }
    }

    override fun onStop() {
        super.onStop()
        viewModel.fusedClient.removeLocationUpdates(viewModel.locationCallback)
        viewModel.needAnimateCamera = false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
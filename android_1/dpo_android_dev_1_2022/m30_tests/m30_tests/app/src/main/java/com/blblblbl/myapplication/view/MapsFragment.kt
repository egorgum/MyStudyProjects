package com.blblblbl.myapplication.view

import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.blblblbl.myapplication.R
import com.blblblbl.myapplication.databinding.DialogPlaceInfoBinding
import com.blblblbl.myapplication.databinding.FragmentMapsBinding
import com.blblblbl.myapplication.presentation.MapsViewModel
import com.bumptech.glide.Glide
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext


@AndroidEntryPoint
class MapsFragment() : Fragment() {

    lateinit var binding: FragmentMapsBinding
    private val viewModel:MapsViewModel by viewModels()
    private lateinit var googleMap1:GoogleMap
    private lateinit var bottomSheetDialog :BottomSheetDialog
    private lateinit var dialogBinding:DialogPlaceInfoBinding
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        with(googleMap.uiSettings){
            isZoomControlsEnabled = true
            isMyLocationButtonEnabled = true
        }
        googleMap.isMyLocationEnabled = true
        //zoomToUser()
        googleMap.setOnMyLocationButtonClickListener {
            var locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val mGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if(!mGPS){
                Log.d("maps","aaaaa")
                Toast.makeText(requireContext(),"enable location on your phone",Toast.LENGTH_SHORT).show()
            }
            else zoomToUser()
            return@setOnMyLocationButtonClickListener mGPS
        }
        googleMap.setOnCameraIdleListener { if (viewModel.showMarkers) loadMarkers() }
        googleMap.setOnMarkerClickListener { marker ->
            if (marker.isInfoWindowShown) {
                marker.hideInfoWindow()
            } else {
                marker.showInfoWindow()
            }
            marker.snippet?.let {
                viewModel.markerOnClick(it)
                bottomSheetDialog.show()
            }
            true
        }
        binding.switchMarkers.setOnCheckedChangeListener { compoundButton, b ->
            viewModel.showMarkers = b
            if (!b) googleMap.clear()
            else {
                loadMarkers()
                addMarkersOnMap()
            }
        }
        googleMap1=googleMap
    }
    private fun addMarkersOnMap() {
        viewModel.places.value?.forEach { place ->val pos = LatLng(place.point?.lat!!, place.point?.lon!!)
            googleMap1.addMarker(
                MarkerOptions()
                    .position(pos)
                    .snippet(place.xid)
                    .title("${place.name}") )}
    }
    fun zoomToUser(){
        var fusedLocationClient = context?.let { LocationServices.getFusedLocationProviderClient(it) }
        var location = fusedLocationClient?.getLastLocation()
        location?.addOnCompleteListener{
            if (it.isSuccessful){
                try {
                    val myLoc = LatLng(location.result!!.latitude, location.result.longitude)
                    googleMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc,15f))
                }
                catch (e:Throwable){}
            }
        }
    }
    fun loadMarkers(){
        val bounds: LatLngBounds = googleMap1.projection.visibleRegion.latLngBounds
        viewModel.loadMarkers(
            lon_min = bounds.southwest.longitude,
            lat_min = bounds.southwest.latitude,
            lon_max = bounds.northeast.longitude,
            lat_max = bounds.northeast.latitude)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapsBinding.inflate(layoutInflater)

        bottomSheetDialog = BottomSheetDialog(this.requireContext())
        dialogBinding = DialogPlaceInfoBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(dialogBinding.root)
        lifecycleScope.launchWhenStarted {
            viewModel.places.collect{places->
                Log.d("MyLog","maps fragment${places.toString()}")
                if (::googleMap1.isInitialized) {
                    //googleMap1.clear()
                    places?.forEach { place ->
                        val pos = LatLng(place.point?.lat!!, place.point?.lon!!)
                        googleMap1.addMarker(
                            MarkerOptions()
                                .position(pos)
                                .snippet(place.xid)
                                .title("${place.name}")
                        )
                    }
                }

            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.placeWithInfo.collect{
                place->
                val regex = "</?.*?>".toRegex()
                dialogBinding.tvDescription.text = place?.info?.descr?.replace(regex,"")
                Glide.with(dialogBinding.ivMain.context).load(place?.image).into(dialogBinding.ivMain)
                Log.d("MyLog",place?.info.toString())
            }
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}
package com.example.myapplication.firstFragment.main.MapFragment.ui

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import com.example.myapplication.firstFragment.main.MapFragment.models.Sight
import com.example.myapplication.firstFragment.main.MapFragment.repo.Repository
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(private val repo: Repository) : ViewModel() {
    //Геолокация
    var locationListener: LocationSource.OnLocationChangedListener? = null
    var map: GoogleMap? = null
    lateinit var fusedClient: FusedLocationProviderClient
    var needAnimateCamera = false
    var needMoveCamera = true
    var handler = Handler(Looper.getMainLooper())
    var cameraMoveRunnable =  Runnable{
        needMoveCamera = true

    }

    val locationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            p0.lastLocation?.let { location ->
                locationListener?.onLocationChanged(location)
                val cameraUpdate =
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(location.latitude,location.longitude),
                        18f)
                if (needMoveCamera){
                    if (needAnimateCamera){
                        map?.animateCamera(cameraUpdate)
                    }
                    else{
                        needAnimateCamera = true
                        map?.moveCamera(cameraUpdate)
                    }
                }
            }
        }
    }
    @SuppressLint("MissingPermission")
    fun startLocation(){
        map?.isMyLocationEnabled = true
        val request = LocationRequest.create().setInterval(1_000).setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
        fusedClient.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper())
    }


    //Достопримечательности
    private var _sights = MutableStateFlow<List<Sight>>(emptyList())
    private val sights = _sights.asStateFlow()
    suspend fun genMarkers(){
        _sights.value = repo.getData()
        sights.value.forEach {
            map?.addMarker(MarkerOptions().position(LatLng(it.point.lat,it.point.lon)).title(it.name))
        }
    }
}
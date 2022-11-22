package com.example.marketlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MyLocations : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_locations)
        createFragment()

    }


    private fun createFragment(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker()
    }
    private fun createMarker(){
        val coordinatesWaltmart = LatLng(20.686298555274064, -103.39574588949533)
        val coordinatesFresko = LatLng(20.676260649192947, -103.39289202152928)
        val coordinatesSoriana = LatLng(20.685374252165307, -103.38885798161708)


        val marker = MarkerOptions().position(coordinatesWaltmart).title("Waltmart")
        val marker2 = MarkerOptions().position(coordinatesFresko).title("Fresko")
        val marker3 = MarkerOptions().position(coordinatesSoriana).title("Soriana")

        map.addMarker(marker)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinatesWaltmart, 18f), 4000, null
        )

        map.addMarker(marker2)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinatesWaltmart, 18f), 4000, null
        )

        map.addMarker(marker3)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinatesWaltmart, 18f), 4000, null
        )
    }
}
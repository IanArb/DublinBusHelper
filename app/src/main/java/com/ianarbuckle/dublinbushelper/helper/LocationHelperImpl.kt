package com.ianarbuckle.dublinbushelper.helper

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat

import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ui.IconGenerator
import com.ianarbuckle.dublinbushelper.R
import com.ianarbuckle.dublinbushelper.utils.Constants
import com.ianarbuckle.dublinbushelper.utils.PermissionsManager

/**
 * Created by Ian Arbuckle on 19/02/2017.

 */

class LocationHelperImpl(private val context: Context) : LocationHelper, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private var map: GoogleMap? = null
    private var googleApiClient: GoogleApiClient? = null
    private lateinit var locationRequest: LocationRequest
    private lateinit var lastLocation: Location
    private var currentLocation: Marker? = null

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun checkLocationPermission(fragment: Fragment): Boolean {
        val accessPermissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        if (PermissionsManager.checkPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
            PermissionsManager.requestPermissions(fragment, Constants.PERMISSION_REQUEST_ACCESS_LOCATION, *accessPermissions)
            return false
        } else {
            return true
        }
    }

    @SuppressLint("MissingPermission")
    override fun initMap(googleMap: GoogleMap) {
        map = googleMap

        if (isLocationPermissionGranted) {
            buildGoogleApiClient()
            map?.isMyLocationEnabled = true
        } else {
            if (isLocationPermissionGranted) {
                buildGoogleApiClient()
                map?.isMyLocationEnabled = true
            }
        }
    }

    @Synchronized protected fun buildGoogleApiClient() {
        googleApiClient = GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()

        googleApiClient?.connect()
    }

    @SuppressLint("MissingPermission")
    override fun onConnected(bundle: Bundle?) {
        locationRequest = LocationRequest()
        locationRequest.interval = 1000
        locationRequest.fastestInterval = 1000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        if (isLocationPermissionGranted) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this)
        }
    }

    private val isLocationPermissionGranted: Boolean
        get() = PermissionsManager.isLocationPermissionGranted(context)

    override fun onConnectionSuspended(interval: Int) {

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }

    override fun onLocationChanged(location: Location) {
        lastLocation = location
        currentLocation?.remove()

        val latLng = LatLng(location.latitude, location.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        currentLocation = map?.addMarker(markerOptions)

        val iconGenerator = IconGenerator(context)

        iconGenerator.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_location_on))

        val icon = BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon())
        currentLocation?.setIcon(icon)

        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
        map?.animateCamera(CameraUpdateFactory.zoomTo(16f))

        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this)
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermission() {
        buildGoogleApiClient()
        if (isLocationPermissionGranted) {
            map?.isMyLocationEnabled = true
        }
    }
}

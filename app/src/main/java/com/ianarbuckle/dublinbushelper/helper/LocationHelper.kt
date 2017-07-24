package com.ianarbuckle.dublinbushelper.helper

import android.support.v4.app.Fragment

import com.google.android.gms.maps.GoogleMap

/**
 * Created by Ian Arbuckle on 19/02/2017.

 */

interface LocationHelper {
    fun checkLocationPermission(fragment: Fragment): Boolean
    fun initMap(googleMap: GoogleMap)
    fun onRequestPermission()
}

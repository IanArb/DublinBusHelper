package com.ianarbuckle.dublinbushelper.firebase.database

/**
 * Created by Ian Arbuckle on 04/03/2017.

 */

interface DatabaseHelper {
    fun sendFavouriteStopToDatabase(time: String, stopId: String, name: String, routes: String, lat: Float, lon: Float)
}

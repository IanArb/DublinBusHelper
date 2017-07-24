package com.ianarbuckle.dublinbushelper.firebase.database

import com.google.firebase.database.FirebaseDatabase
import com.ianarbuckle.dublinbushelper.models.Favourites

/**
 * Created by Ian Arbuckle on 04/03/2017.

 */

class DatabaseHelperImpl(private val firebaseDatabase: FirebaseDatabase) : DatabaseHelper {

    override fun sendFavouriteStopToDatabase(time: String, stopId: String, name: String, routes: String, lat: Float, lon: Float) {
        val favourites = Favourites(time, stopId, name, routes, lat, lon)
        firebaseDatabase.getReference("favourites").push().setValue(favourites)
    }
}

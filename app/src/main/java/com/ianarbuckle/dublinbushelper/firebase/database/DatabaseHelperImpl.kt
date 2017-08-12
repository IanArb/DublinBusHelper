package com.ianarbuckle.dublinbushelper.firebase.database

import com.google.firebase.database.FirebaseDatabase
import com.ianarbuckle.dublinbushelper.models.Favourites

/**
 * Created by Ian Arbuckle on 04/03/2017.

 */

class DatabaseHelperImpl(private val firebaseDatabase: FirebaseDatabase) : DatabaseHelper {

    override fun sendFavouriteStopToDatabase(favourites: Favourites, directory: String) {
        val latitude = favourites.latitude
        val longitude = favourites.longitude
        val name = favourites.name
        val routes = favourites.routes
        val stopId = favourites.stopId
        val time = favourites.time
        Favourites(time, stopId, name, routes, latitude, longitude)
        firebaseDatabase.getReference(directory).push().setValue(favourites)
    }
}

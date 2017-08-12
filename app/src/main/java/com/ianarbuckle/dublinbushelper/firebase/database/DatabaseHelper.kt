package com.ianarbuckle.dublinbushelper.firebase.database

import com.ianarbuckle.dublinbushelper.models.Favourites

/**
 * Created by Ian Arbuckle on 04/03/2017.

 */

interface DatabaseHelper {
    fun sendFavouriteStopToDatabase(favourites: Favourites, directory: String)
}

package com.ianarbuckle.dublinbushelper.models

/**
 * Created by Ian Arbuckle on 03/04/2017.

 */

data class Favourites(val time: String = "", val stopId: String = "", val name: String = "",
                      val routes: String = "", val latitude: Float = 0.toFloat(), val longitude: Float = 0.toFloat())

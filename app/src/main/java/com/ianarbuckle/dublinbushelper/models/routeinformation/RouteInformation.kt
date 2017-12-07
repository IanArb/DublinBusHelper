package com.ianarbuckle.dublinbushelper.models.routeinformation

import com.squareup.moshi.Json

/**
 * Created by Ian Arbuckle on 15/10/2017.
 *
 */

data class RouteInformation(@Json(name = "errorcode")
                            val errorcode: String?,
                            @Json(name = "errormessage")
                            val errormessage: String?,
                            @Json(name = "numberofresults")
                            val numberofresults: Int?,
                            @Json(name = "route")
                            val route: String?,
                            @Json(name = "timestamp")
                            val timestamp: String?,
                            @Json(name = "results")
                            val results: List<Result>?)


package com.ianarbuckle.dublinbushelper.models.routelistinformation

import com.squareup.moshi.Json

/**
 * Created by Ian Arbuckle on 15/10/2017.
 *
 */

data class RouteListInformation(@Json(name = "operator")
                                val operator: String?,
                                @Json(name = "route")
                                val route: String?)

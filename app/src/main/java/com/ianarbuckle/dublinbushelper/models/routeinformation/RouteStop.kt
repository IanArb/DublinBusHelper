package com.ianarbuckle.dublinbushelper.models.routeinformation

import com.squareup.moshi.Json

/**
 * Created by Ian Arbuckle on 15/10/2017.
 *
 */

data class RouteStop(@Json(name = "stopid")
                     val stopid: String?,
                     @Json(name = "displaystopid")
                     val displaystopid: String?,
                     @Json(name = "shortname")
                     val shortname: String?,
                     @Json(name = "shortnamelocalized")
                     val shortnamelocalized: String?,
                     @Json(name = "fullname")
                     val fullname: String?,
                     @Json(name = "fullnamelocalized")
                     val fullnamelocalized: String?,
                     @Json(name = "latitude")
                     val latitude: String?,
                     @Json(name = "longitude")
                     val longitude: String?,
                     @Json(name = "operators")
                     val operators: List<Operator>?)

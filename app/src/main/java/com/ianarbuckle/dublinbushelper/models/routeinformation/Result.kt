package com.ianarbuckle.dublinbushelper.models.routeinformation

import com.squareup.moshi.Json

/**
 * Created by Ian Arbuckle on 15/10/2017.
 *
 */

data class Result(@Json(name = "operator")
                  val operator: String?,
                  @Json(name = "origin")
                  val origin: String?,
                  @Json(name = "originlocalized")
                  val originlocalized: String?,
                  @Json(name = "destination")
                  val destination: String?,
                  @Json(name = "destinationlocalized")
                  val destinationlocalized: String?,
                  @Json(name = "lastupdated")
                  val lastupdated: String?,
                  @Json(name = "routeStops")
                  val routeStops: List<RouteStop>?)

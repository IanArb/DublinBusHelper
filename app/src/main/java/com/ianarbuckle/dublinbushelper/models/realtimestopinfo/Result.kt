package com.ianarbuckle.dublinbushelper.models.realtimestopinfo

import com.squareup.moshi.Json

data class Result(@Json(name = "arrivaldatetime") val arrivaldatetime: String? = "",
                  @Json(name = "duetime") val duetime: String? = "",
                  @Json(name = "departuredatetime") val departuredatetime: String? = "",
                  @Json(name = "departureduetime") val departureduetime: String? = "",
                  @Json(name = "scheduledarrivaldatetime") val scheduledarrivaldatetime: String? = "",
                  @Json(name = "scheduleddeparturedatetime") val scheduleddeparturedatetime: String? = "",
                  @Json(name = "destination") val destination: String? = "",
                  @Json(name = "destinationlocalized") val destinationlocalized: String? = "",
                  @Json(name = "origin") val origin: String? = "",
                  @Json(name = "originlocalized") val originlocalized: String? = "",
                  @Json(name = "direction") val direction: String? = "",
                  @Json(name = "operator") val operator: String? = "",
                  @Json(name = "additionalinformation") val additionalinformation: String? = "",
                  @Json(name = "lowfloorstatus") val lowfloorstatus: String? = "",
                  @Json(name = "route") val route: String? = "",
                  @Json(name = "sourcetimestamp") val sourcetimestamp: String? = "",
                  @Json(name = "monitored") val monitored: String? = ""
             )

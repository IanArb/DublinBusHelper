package com.ianarbuckle.dublinbushelper.models.stopinfo

import com.squareup.moshi.Json

data class Result(@Json(name = "stopid") val stopid: String? = "",
                  @Json(name = "displaystopid") val displaystopid: String? = "",
                  @Json(name = "shortname") val shortname: String? = "",
                  @Json(name = "shortnamelocalized") val shortnamelocalized: String? = "",
                  @Json(name = "fullname") val fullname: String? = "",
                  @Json(name = "fullnamelocalized") val fullnamelocalized: String? = "",
                  @Json(name = "latitude") val latitude: Float? = 0.toFloat(),
                  @Json(name = "longitude") val longitude: Float? = 0.toFloat(),
                  @Json(name = "lastupdated") val lastupdated: String? = "",
                  @Json(name = "operators") val operators: List<Operator>? = emptyList())

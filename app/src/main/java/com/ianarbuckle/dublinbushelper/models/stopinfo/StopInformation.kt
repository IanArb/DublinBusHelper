package com.ianarbuckle.dublinbushelper.models.stopinfo

import com.squareup.moshi.Json

data class StopInformation(@Json(name = "errorcode") val errorcode: String? = "",
                           @Json(name = "errormessage") val errormessage: String? = "",
                           @Json(name = "numberofresults") val numberofresults: String? = "",
                           @Json(name = "timestamp") val timestamp: String? = "",
                           @Json(name = "results") val results: List<Result> = emptyList())

package com.ianarbuckle.dublinbushelper.models.realtimestopinfo

import com.squareup.moshi.Json

data class RealTimeInfo(@Json(name = "errorcode") val errorcode: String? = "",
                        @Json(name = "numberofresults") val numberofresults: String? = "",
                        @Json(name = "errormessage") val errormessage: String? = "",
                        @Json(name = "stopid") val stopid: String? = "",
                        @Json(name = "timestamp") val timestamp: String? = "",
                        @Json(name = "results") val results: List<Result> = emptyList())

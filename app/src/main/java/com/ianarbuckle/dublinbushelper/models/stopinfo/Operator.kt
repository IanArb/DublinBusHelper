package com.ianarbuckle.dublinbushelper.models.stopinfo

import com.squareup.moshi.Json

data class Operator(@Json(name = "name") val name: String? = "",
                    @Json(name = "routes") val routes: List<String>? = emptyList())

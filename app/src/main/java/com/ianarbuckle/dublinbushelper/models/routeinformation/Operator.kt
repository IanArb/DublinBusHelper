package com.ianarbuckle.dublinbushelper.models.routeinformation

import com.squareup.moshi.Json

/**
 * Created by Ian Arbuckle on 15/10/2017.
 *
 */

data class Operator(@Json(name ="name") val name: String?,
                    @Json(name = "routes") val routes: List<String>?)

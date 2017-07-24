package com.ianarbuckle.dublinbushelper.network


import com.ianarbuckle.dublinbushelper.models.stopinfo.StopInformation
import com.ianarbuckle.dublinbushelper.models.realtimestopinfo.RealTimeInfo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by Ian Arbuckle on 20/02/2017.

 */

interface RTPIServiceAPI {

    @GET("cgi-bin/rtpi/busstopinformation")
    fun getStopInfo(@QueryMap filters: Map<String, String>): Call<StopInformation>

    @GET("cgi-bin/rtpi/realtimebusinformation")
    fun getRealTimeInfo(@QueryMap filters: Map<String, String>): Call<RealTimeInfo>

}

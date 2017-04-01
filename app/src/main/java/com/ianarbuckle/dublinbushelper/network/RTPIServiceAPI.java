package com.ianarbuckle.dublinbushelper.network;


import com.ianarbuckle.dublinbushelper.models.stopinfo.StopInformation;
import com.ianarbuckle.dublinbushelper.models.realtimestopinfo.RealTimeInfo;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Ian Arbuckle on 20/02/2017.
 *
 */

public interface RTPIServiceAPI {

  @GET("cgi-bin/rtpi/busstopinformation")
  Call<StopInformation> getStopInfo(@QueryMap Map<String, String> filters);

  @GET("cgi-bin/rtpi/realtimebusinformation")
  Call<RealTimeInfo> getRealTimeInfo(@QueryMap Map<String, String> filters);

}

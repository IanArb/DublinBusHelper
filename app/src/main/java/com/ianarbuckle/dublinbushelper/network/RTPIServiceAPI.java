package com.ianarbuckle.dublinbushelper.network;


import com.ianarbuckle.dublinbushelper.models.busstopinfo.BusStopInformation;
import com.ianarbuckle.dublinbushelper.models.realtimebusstopinfo.RealTimeBusInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ian Arbuckle on 20/02/2017.
 *
 */

public interface RTPIServiceAPI {

  @GET("cgi-bin/rtpi/busstopinformation?format=json")
  Call<BusStopInformation> getBusStopInfo();

  @GET("cgi-bin/rtpi/realtimebusinformation&format=json")
  Call<List<RealTimeBusInfo>> getRealTimeBusInfo(@Query("stopid") String stopid);

}

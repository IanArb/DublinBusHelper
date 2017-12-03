package com.ianarbuckle.dublinbushelper.network;

import com.ianarbuckle.dublinbushelper.models.realtimestopinfo.RealTimeInfo;
import com.ianarbuckle.dublinbushelper.models.routeinformation.RouteInformation;
import com.ianarbuckle.dublinbushelper.models.routelistinformation.RouteListInformation;
import com.ianarbuckle.dublinbushelper.models.stopinfo.StopInformation;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Ian Arbuckle on 17/08/2017.
 *
 */

public interface RealTimePassengerInfoService {

  @GET("cgi-bin/rtpi/busstopinformation")
  Observable<StopInformation> getStopInfo(@QueryMap Map<String, String> stringMap);

  @GET("cgi-bin/rtpi/realtimebusinformation")
  Observable<RealTimeInfo> getRealTimeInfo(@QueryMap Map<String, String> stringMap);

  @GET("cgi-bin/rtpi/routelistInformation")
  Observable<RouteListInformation> getRouteListInfo(@Query("operator") String operator);

  @GET("cgi-bin/rtpi/routeinformation")
  Observable<RouteInformation> getRouteInfo(@QueryMap Map<String, String> stringMap);

}

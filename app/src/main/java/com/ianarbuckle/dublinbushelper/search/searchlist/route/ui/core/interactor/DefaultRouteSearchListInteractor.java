package com.ianarbuckle.dublinbushelper.search.searchlist.route.ui.core.interactor;

import com.ianarbuckle.dublinbushelper.models.routeinformation.RouteInformation;
import com.ianarbuckle.dublinbushelper.models.routelistinformation.RouteListInformation;
import com.ianarbuckle.dublinbushelper.network.RealTimePassengerInfoAPI;
import com.ianarbuckle.dublinbushelper.utils.Constants;

import java.util.Map;

import rx.Observable;

/**
 * Created by Ian Arbuckle on 30/10/2017.
 *
 */

public class DefaultRouteSearchListInteractor implements RouteSearchListInteractor {

  private final RealTimePassengerInfoAPI passengerInfoAPI;

  public DefaultRouteSearchListInteractor(RealTimePassengerInfoAPI passengerInfoAPI) {
    this.passengerInfoAPI = passengerInfoAPI;
  }

  @Override
  public Observable<RouteListInformation> fetchRouteListInformation() {
    return passengerInfoAPI.getRouteListInformation(Constants.OPERATOR_VALUE_BUS);
  }

  @Override
  public Observable<RouteInformation> fetchRouteInformation(Map<String, String> stringMap) {
    return passengerInfoAPI.getRouteInformation(stringMap);
  }
}

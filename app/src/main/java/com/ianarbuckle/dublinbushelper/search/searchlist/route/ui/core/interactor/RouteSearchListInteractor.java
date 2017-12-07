package com.ianarbuckle.dublinbushelper.search.searchlist.route.ui.core.interactor;

import com.ianarbuckle.dublinbushelper.models.routeinformation.RouteInformation;
import com.ianarbuckle.dublinbushelper.models.routelistinformation.RouteListInformation;

import java.util.Map;

import rx.Observable;


/**
 * Created by Ian Arbuckle on 30/10/2017.
 *
 */

public interface RouteSearchListInteractor {
  Observable<RouteListInformation> fetchRouteListInformation();
  Observable<RouteInformation> fetchRouteInformation(Map<String, String> stringMap);
}

package com.ianarbuckle.dublinbushelper.search.dublinbus.stop.builder;

import com.ianarbuckle.dublinbushelper.TransportHelperApplication;
import com.ianarbuckle.dublinbushelper.search.dublinbus.route.SearchRouteFragment;
import com.ianarbuckle.dublinbushelper.search.dublinbus.route.ui.builder.DaggerSearchBusRouteComponent;
import com.ianarbuckle.dublinbushelper.search.dublinbus.route.ui.builder.SearchBusRouteModule;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.SearchStopFragment;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.ui.builder.DaggerSearchBusStopComponent;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.ui.builder.SearchStopModule;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */

public class DefaultSearchStopInjector implements SearchStopInjector {

  @Override
  public void inject(SearchStopFragment fragment) {
    DaggerSearchBusStopComponent.builder()
        .applicationComponent(TransportHelperApplication.get(fragment).getApplicationComponent())
        .searchStopModule(new SearchStopModule(fragment))
        .build()
        .inject(fragment);
  }

  @Override
  public void inject(SearchRouteFragment fragment) {
    DaggerSearchBusRouteComponent.builder()
        .applicationComponent(TransportHelperApplication.get(fragment).getApplicationComponent())
        .searchBusRouteModule(new SearchBusRouteModule(fragment))
        .build()
        .inject(fragment);
  }
}

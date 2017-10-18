package com.ianarbuckle.dublinbushelper.search.dublinbus.route.builder;

import com.ianarbuckle.dublinbushelper.TransportHelperApplication;
import com.ianarbuckle.dublinbushelper.search.dublinbus.route.SearchRouteFragment;
import com.ianarbuckle.dublinbushelper.search.dublinbus.route.ui.builder.DaggerSearchBusRouteComponent;
import com.ianarbuckle.dublinbushelper.search.dublinbus.route.ui.builder.SearchBusRouteModule;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */

public class DefaultSearchBusRouteInjector implements SearchBusRouteInjector {

  @Override
  public void inject(SearchRouteFragment fragment) {
    DaggerSearchBusRouteComponent.builder()
        .applicationComponent(TransportHelperApplication.get(fragment).getApplicationComponent())
        .searchBusRouteModule(new SearchBusRouteModule())
        .build()
        .inject(fragment);
  }
}

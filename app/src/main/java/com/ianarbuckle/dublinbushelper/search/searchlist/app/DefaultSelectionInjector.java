package com.ianarbuckle.dublinbushelper.search.searchlist.app;

import com.ianarbuckle.dublinbushelper.TransportHelperApplication;
import com.ianarbuckle.dublinbushelper.search.searchlist.route.RouteSearchListActivity;
import com.ianarbuckle.dublinbushelper.search.searchlist.route.ui.core.builder.DaggerRouteSearchListComponent;
import com.ianarbuckle.dublinbushelper.search.searchlist.route.ui.core.builder.RouteSearchListModule;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */

public class DefaultSelectionInjector implements SelectionInjector {

  @Override
  public void inject(RouteSearchListActivity activity) {
    DaggerRouteSearchListComponent.builder()
        .applicationComponent(TransportHelperApplication.get(activity).getApplicationComponent())
        .routeSearchListModule(new RouteSearchListModule(activity))
        .build()
        .inject(activity);
  }
}

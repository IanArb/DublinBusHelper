package com.ianarbuckle.dublinbushelper.search.dublinbus.route.ui.builder;

import com.ianarbuckle.dublinbushelper.ApplicationComponent;
import com.ianarbuckle.dublinbushelper.search.dublinbus.route.SearchRouteFragment;

import dagger.Component;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */
@SearchBusRouteScope
@Component(modules = SearchBusRouteModule.class, dependencies = ApplicationComponent.class)
public interface SearchBusRouteComponent {
  void inject(SearchRouteFragment fragment);
}

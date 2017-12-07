package com.ianarbuckle.dublinbushelper.search.dublinbus.stop.ui.builder;

import com.ianarbuckle.dublinbushelper.ApplicationComponent;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.SearchStopFragment;

import dagger.Component;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */
@SearchBusStopScope
@Component(modules = SearchStopModule.class, dependencies = ApplicationComponent.class)
public interface SearchBusStopComponent {
  void inject(SearchStopFragment fragment);
}

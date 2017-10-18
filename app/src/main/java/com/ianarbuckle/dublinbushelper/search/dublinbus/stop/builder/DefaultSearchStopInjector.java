package com.ianarbuckle.dublinbushelper.search.dublinbus.stop.builder;

import com.ianarbuckle.dublinbushelper.TransportHelperApplication;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.SearchStopFragment;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.ui.dagger.DaggerSearchBusStopComponent;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.ui.dagger.SearchStopModule;

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
}

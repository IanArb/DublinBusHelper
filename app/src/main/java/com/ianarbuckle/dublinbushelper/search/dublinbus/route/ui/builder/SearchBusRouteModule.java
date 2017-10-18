package com.ianarbuckle.dublinbushelper.search.dublinbus.route.ui.builder;

import android.content.Context;

import com.ianarbuckle.dublinbushelper.search.dublinbus.route.builder.DefaultSearchBusRouteInjector;
import com.ianarbuckle.dublinbushelper.search.dublinbus.route.builder.SearchBusRouteInjector;
import com.ianarbuckle.dublinbushelper.search.dublinbus.route.ui.DefaultSearchRouteView;
import com.ianarbuckle.dublinbushelper.search.dublinbus.route.ui.SearchRouteView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */
@SearchBusRouteScope
@Module
public class SearchBusRouteModule {

  @Provides
  SearchRouteView providesRouteView(Context context) {
    return new DefaultSearchRouteView(context);
  }

  @Provides
  SearchBusRouteInjector provideInjector() {
    return new DefaultSearchBusRouteInjector();
  }
}

package com.ianarbuckle.dublinbushelper.search.dublinbus.route;

import com.ianarbuckle.dublinbushelper.search.dublinbus.route.builder.SearchBusRouteInjector;

import javax.annotation.Nonnull;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */

public class SearchRouteProvider {
  private static SearchBusRouteInjector injector;

  public static SearchRouteProvider init(@Nonnull SearchBusRouteInjector injector) {
    SearchRouteProvider.injector = injector;
    return new SearchRouteProvider();
  }

  public static SearchBusRouteInjector get() {
    if(injector == null) {
      throw new IllegalStateException("Did you forget to call SearchBusRouteInjecter.init in your class?");
    }
    return injector;
  }
}

package com.ianarbuckle.dublinbushelper.search.searchlist.route.ui.core.builder;

import com.ianarbuckle.dublinbushelper.network.RealTimePassengerInfoAPI;
import com.ianarbuckle.dublinbushelper.search.searchlist.route.RouteSearchListView;
import com.ianarbuckle.dublinbushelper.search.searchlist.route.ui.core.interactor.DefaultRouteSearchListInteractor;
import com.ianarbuckle.dublinbushelper.search.searchlist.route.ui.core.interactor.RouteSearchListInteractor;
import com.ianarbuckle.dublinbushelper.search.searchlist.route.ui.core.presenter.DefaultRouteSearchListPresenter;
import com.ianarbuckle.dublinbushelper.search.searchlist.route.ui.core.presenter.RouteSearchListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ian Arbuckle on 30/10/2017.
 *
 */
@Module
public class RouteSearchListModule {

  private final RouteSearchListView view;

  public RouteSearchListModule(RouteSearchListView view) {
    this.view = view;
  }

  @Provides
  public RouteSearchListInteractor providesInteractor(RealTimePassengerInfoAPI realTimePassengerInfoAPI) {
    return new DefaultRouteSearchListInteractor(realTimePassengerInfoAPI);
  }

  @Provides
  public RouteSearchListPresenter providesPresenter(RouteSearchListInteractor interactor) {
    return new DefaultRouteSearchListPresenter(view, interactor);
  }
}

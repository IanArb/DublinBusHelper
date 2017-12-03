package com.ianarbuckle.dublinbushelper.search.searchlist.stop.ui.builder;

import com.ianarbuckle.dublinbushelper.network.RealTimePassengerInfoAPI;
import com.ianarbuckle.dublinbushelper.search.searchlist.stop.ui.StopSearchListView;
import com.ianarbuckle.dublinbushelper.search.searchlist.stop.ui.core.interactor.DefaultStopSearchInteractor;
import com.ianarbuckle.dublinbushelper.search.searchlist.stop.ui.core.interactor.StopSearchInteractor;
import com.ianarbuckle.dublinbushelper.search.searchlist.stop.ui.core.presenter.DefaultStopSearchPresenter;
import com.ianarbuckle.dublinbushelper.search.searchlist.stop.ui.core.presenter.StopSearchPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */
@Module
public class StopSearchListModule {

  private final StopSearchListView view;

  public StopSearchListModule(StopSearchListView view) {
    this.view = view;
  }

  @StopSearchListScope
  @Provides
  StopSearchInteractor providesStopSearchInteractor(RealTimePassengerInfoAPI realTimePassengerInfoAPI) {
    return new DefaultStopSearchInteractor(realTimePassengerInfoAPI);
  }

  @StopSearchListScope
  @Provides
  StopSearchPresenter providesStopSearchPresenter(StopSearchInteractor interactor) {
    return new DefaultStopSearchPresenter(view, interactor);
  }

}

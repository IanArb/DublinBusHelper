package com.ianarbuckle.dublinbushelper.transports.irishrail.dagger;

import com.ianarbuckle.dublinbushelper.firebase.database.DatabaseHelper;
import com.ianarbuckle.dublinbushelper.network.RealTimePassengerInfoAPI;
import com.ianarbuckle.dublinbushelper.transports.irishrail.RailPresenterImpl;
import com.ianarbuckle.dublinbushelper.transports.irishrail.RailView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ian Arbuckle on 12/08/2017.
 *
 */
@Module
public class RailModule {
  private RailView view;

  public RailModule(RailView view) {
    this.view = view;
  }

  @RailScope
  @Provides
  RailView provideView() {
    return view;
  }

  @RailScope
  @Provides
  RailPresenterImpl providesPresenter(RailView view, DatabaseHelper databaseHelper, RealTimePassengerInfoAPI realTimePassengerInfoAPI) {
    return new RailPresenterImpl(view, databaseHelper, realTimePassengerInfoAPI);
  }
}

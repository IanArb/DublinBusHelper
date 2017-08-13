package com.ianarbuckle.dublinbushelper.transports.irishrail.di;

import com.ianarbuckle.dublinbushelper.TransportHelperApplication;
import com.ianarbuckle.dublinbushelper.firebase.database.DatabaseHelper;
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
  RailPresenterImpl providesPresenter(RailView view, DatabaseHelper databaseHelper) {
    return new RailPresenterImpl(view, databaseHelper);
  }

  @RailScope
  @Provides
  DatabaseHelper provideDatabaseHelper() {
    return TransportHelperApplication.getAppInstance().getDatabaseHelper();
  }
}

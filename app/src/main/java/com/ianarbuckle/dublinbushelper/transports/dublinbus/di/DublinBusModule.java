package com.ianarbuckle.dublinbushelper.transports.dublinbus.di;

import com.ianarbuckle.dublinbushelper.firebase.database.DatabaseHelper;
import com.ianarbuckle.dublinbushelper.helper.LocationHelper;
import com.ianarbuckle.dublinbushelper.models.stopinfo.Result;
import com.ianarbuckle.dublinbushelper.models.stopinfo.StopInformation;
import com.ianarbuckle.dublinbushelper.transports.dublinbus.DialogCallback;
import com.ianarbuckle.dublinbushelper.transports.dublinbus.DublinBusPresenterImpl;
import com.ianarbuckle.dublinbushelper.transports.dublinbus.DublinBusView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ian Arbuckle on 12/08/2017.
 *
 */
@Module
public class DublinBusModule {
  private DublinBusView view;
  private DialogCallback callback;

  public DublinBusModule(DublinBusView view) {
    this.view = view;
  }

  public DublinBusModule(DialogCallback callback) {
    this.callback = callback;
  }

  @DublinBusScope
  @Provides
  DublinBusView provideView(DublinBusView view) {
    return view;
  }

  @DublinBusScope
  @Provides
  DialogCallback provideCallback(DialogCallback callback) {
    return callback;
  }

  @DublinBusScope
  @Provides @Named("fragmentPresenter")
  DublinBusPresenterImpl providePresenter(DatabaseHelper databaseHelper, LocationHelper locationHelper) {
    return new DublinBusPresenterImpl(databaseHelper, locationHelper, view);
  }

  @DublinBusScope
  @Provides @Named("dialogPresenter")
  DublinBusPresenterImpl providePresenterDialog(DatabaseHelper databaseHelper) {
    return new DublinBusPresenterImpl(databaseHelper, callback);
  }

  @DublinBusScope
  @Provides
  StopInformation provideStopInformation() {
    return new StopInformation();
  }

  @DublinBusScope
  @Provides
  List<Result> provideResultList() {
    return new ArrayList<>();
  }

}

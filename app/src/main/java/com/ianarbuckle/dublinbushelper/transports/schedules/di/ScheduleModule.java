package com.ianarbuckle.dublinbushelper.transports.schedules.di;

import android.content.Context;

import com.ianarbuckle.dublinbushelper.helper.LocationHelper;
import com.ianarbuckle.dublinbushelper.helper.LocationHelperImpl;
import com.ianarbuckle.dublinbushelper.transports.schedules.SchedulePresenterImpl;
import com.ianarbuckle.dublinbushelper.transports.schedules.ScheduleView;


import dagger.Module;
import dagger.Provides;

/**
 * Created by Ian Arbuckle on 12/08/2017.
 *
 */
@Module
public class ScheduleModule {
  private ScheduleView view;

  public ScheduleModule(ScheduleView view) {
    this.view = view;
  }

  @ScheduleScope
  @Provides
  SchedulePresenterImpl providePresenter(LocationHelper locationHelper) {
    return new SchedulePresenterImpl(locationHelper, view);
  }

  @ScheduleScope
  @Provides
  LocationHelper provideLocationHelper(Context context) {
    return new LocationHelperImpl(context);
  }

}

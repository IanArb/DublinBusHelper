package com.ianarbuckle.dublinbushelper.transports.dublinbus.di;

import android.content.Context;

import com.ianarbuckle.dublinbushelper.TransportHelperApplication;
import com.ianarbuckle.dublinbushelper.firebase.database.DatabaseHelper;
import com.ianarbuckle.dublinbushelper.helper.LocationHelper;
import com.ianarbuckle.dublinbushelper.helper.LocationHelperImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ian Arbuckle on 12/08/2017.
 *
 */
@Module
public class HelperModule {

  @DublinBusScope
  @Provides
  LocationHelper provideLocationHelper(Context context) {
    return new LocationHelperImpl(context);
  }

  @DublinBusScope
  @Provides
  DatabaseHelper provideDatabaseHelper() {
    return TransportHelperApplication.getAppInstance().getDatabaseHelper();
  }
}

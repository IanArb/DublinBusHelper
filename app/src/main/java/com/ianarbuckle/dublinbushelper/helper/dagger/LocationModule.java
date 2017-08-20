package com.ianarbuckle.dublinbushelper.helper.dagger;

import android.content.Context;

import com.ianarbuckle.dublinbushelper.ApplicationScope;
import com.ianarbuckle.dublinbushelper.helper.LocationHelper;
import com.ianarbuckle.dublinbushelper.helper.LocationHelperImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ian Arbuckle on 19/08/2017.
 *
 */
@Module
public class LocationModule {

  @ApplicationScope
  @Provides
  LocationHelper provideLocationHelper(Context context) {
    return new LocationHelperImpl(context);
  }
}

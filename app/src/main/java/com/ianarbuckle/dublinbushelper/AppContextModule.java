package com.ianarbuckle.dublinbushelper;

import android.content.Context;

import com.ianarbuckle.dublinbushelper.firebase.dagger.FirebaseModule;
import com.ianarbuckle.dublinbushelper.helper.dagger.LocationModule;
import com.ianarbuckle.dublinbushelper.network.dagger.NetworkModule;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ian Arbuckle on 17/08/2017.
 *
 */
@Module(includes = {NetworkModule.class, FirebaseModule.class, LocationModule.class})
public class AppContextModule {
  private final Context context;

  public AppContextModule(Context context) {
    this.context = context;
  }

  @Provides
  @ApplicationScope
  public Context provideContext() {
    return context;
  }
}

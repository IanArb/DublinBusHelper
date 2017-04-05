package com.ianarbuckle.dublinbushelper;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ian Arbuckle on 20/02/2017.
 *
 */
@Module
public class ApplicationModule {
  private TransportHelperApplication application;

  public ApplicationModule(TransportHelperApplication application) {
    this.application = application;
  }

  @Provides
  public Context provideContext() {
    return application.getApplicationContext();
  }

}

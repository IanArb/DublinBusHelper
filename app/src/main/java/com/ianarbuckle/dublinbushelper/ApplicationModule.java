package com.ianarbuckle.dublinbushelper;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ian Arbuckle on 20/02/2017.
 *
 */
@Module
public class ApplicationModule {
  private final Application application;

  public ApplicationModule(Application application) {
    this.application = application;
  }

  @ApplicationScope
  @Provides
  public Application provideApplication() {
    return application;
  }
}

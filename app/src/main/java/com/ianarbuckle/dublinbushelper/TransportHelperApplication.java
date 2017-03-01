package com.ianarbuckle.dublinbushelper;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by Ian Arbuckle on 20/02/2017.
 *
 */

public class TransportHelperApplication extends Application {

  private ApplicationComponent applicationComponent;


  @Override
  public void onCreate() {
    super.onCreate();

    getApplicationComponent(this);
  }

  private static ApplicationComponent getApplicationComponent(Context context) {
    TransportHelperApplication application = (TransportHelperApplication) context.getApplicationContext();
    if(application.applicationComponent == null) {
      application.applicationComponent = DaggerApplicationComponent.builder()
          .applicationModule(application.getApplicationModule())
          .build();
    }
    return application.applicationComponent;
  }

  protected ApplicationModule getApplicationModule() {
    return new ApplicationModule(this);
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }
}

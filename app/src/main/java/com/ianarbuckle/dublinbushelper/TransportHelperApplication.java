package com.ianarbuckle.dublinbushelper;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;

/**
 * Created by Ian Arbuckle on 20/02/2017.
 *
 */

public class TransportHelperApplication extends Application {

  ApplicationComponent applicationComponent;


  @Override
  public void onCreate() {
    super.onCreate();

    initDagger();
  }

  public static TransportHelperApplication get(Fragment fragment) {
    return (TransportHelperApplication) fragment.getActivity().getApplication();
  }

  public static TransportHelperApplication get(Activity activity) {
    return (TransportHelperApplication) activity.getApplication();
  }

  public void initDagger() {
    applicationComponent = DaggerApplicationComponent.builder()
        .appContextModule(new AppContextModule(this))
        .build();
  }

  public ApplicationComponent getApplicationComponent() {
    return applicationComponent;
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }
}

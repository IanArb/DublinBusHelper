package com.ianarbuckle.dublinbushelper;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.ianarbuckle.dublinbushelper.firebase.authentication.FirebaseAuthHelper;
import com.ianarbuckle.dublinbushelper.firebase.authentication.FirebaseAuthHelperImpl;

/**
 * Created by Ian Arbuckle on 20/02/2017.
 *
 */

public class TransportHelperApplication extends Application {

  private ApplicationComponent applicationComponent;

  private FirebaseAuthHelper firebaseAuthHelper;

  private static TransportHelperApplication appInstance;


  @Override
  public void onCreate() {
    super.onCreate();

    getApplicationComponent(this);

    initFirebaseAuth();
  }

  private void initFirebaseAuth() {
    if(!FirebaseApp.getApps(this).isEmpty()) {
      appInstance = this;
      FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
      firebaseAuthHelper = new FirebaseAuthHelperImpl(firebaseAuth);
    }
  }

  public static TransportHelperApplication getAppInstance() {
    return appInstance;
  }

  public FirebaseAuthHelper getFirebaseAuthHelper() {
    return firebaseAuthHelper;
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

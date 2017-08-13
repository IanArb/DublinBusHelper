package com.ianarbuckle.dublinbushelper;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.ianarbuckle.dublinbushelper.firebase.authentication.FirebaseAuthHelper;
import com.ianarbuckle.dublinbushelper.firebase.authentication.FirebaseAuthHelperImpl;
import com.ianarbuckle.dublinbushelper.firebase.database.DatabaseHelper;
import com.ianarbuckle.dublinbushelper.firebase.database.DatabaseHelperImpl;

/**
 * Created by Ian Arbuckle on 20/02/2017.
 *
 */

public class TransportHelperApplication extends Application {

  private ApplicationComponent applicationComponent;

  private FirebaseAuthHelper firebaseAuthHelper;
  private DatabaseHelper databaseHelper;

  private static TransportHelperApplication appInstance;


  @Override
  public void onCreate() {
    super.onCreate();

    getApplicationComponent(this);

    initFirebaseAuth();
  }

  private void initFirebaseAuth() {
    if(!FirebaseApp.getApps(this).isEmpty()) {
      FirebaseDatabase.getInstance().setPersistenceEnabled(true);
      appInstance = this;
      FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
      FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
      databaseHelper = new DatabaseHelperImpl(firebaseDatabase);
      firebaseAuthHelper = new FirebaseAuthHelperImpl(firebaseAuth);
    }
  }

  public static TransportHelperApplication getAppInstance() {
    return appInstance;
  }

  public DatabaseHelper getDatabaseHelper() {
    return databaseHelper;
  }

  public FirebaseAuthHelper getFirebaseAuthHelper() {
    return firebaseAuthHelper;
  }

  public static ApplicationComponent getApplicationComponent(Context context) {
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

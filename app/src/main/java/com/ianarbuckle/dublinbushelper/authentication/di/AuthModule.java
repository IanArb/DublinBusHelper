package com.ianarbuckle.dublinbushelper.authentication.di;

import com.ianarbuckle.dublinbushelper.TransportHelperApplication;
import com.ianarbuckle.dublinbushelper.firebase.authentication.FirebaseAuthHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ian Arbuckle on 08/08/2017.
 *
 */
@Module
public class AuthModule {

  @AuthScope
  @Provides
  FirebaseAuthHelper firebaseAuthHelper() {
    return TransportHelperApplication.getAppInstance().getFirebaseAuthHelper();
  }
}

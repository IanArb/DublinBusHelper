package com.ianarbuckle.dublinbushelper.authentication.di;

import com.ianarbuckle.dublinbushelper.authentication.register.RegisterPresenterImpl;
import com.ianarbuckle.dublinbushelper.firebase.authentication.FirebaseAuthHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ian Arbuckle on 08/08/2017.
 *
 */
@Module
public class RegisterModule {

  @AuthScope
  @Provides
  RegisterPresenterImpl providesPresenter(FirebaseAuthHelper authHelper) {
    return new RegisterPresenterImpl(authHelper);
  }
}

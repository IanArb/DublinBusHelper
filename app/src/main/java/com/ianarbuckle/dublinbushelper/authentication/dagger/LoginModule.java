package com.ianarbuckle.dublinbushelper.authentication.dagger;

import com.ianarbuckle.dublinbushelper.authentication.login.LoginPresenterImpl;
import com.ianarbuckle.dublinbushelper.firebase.authentication.FirebaseAuthHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ian Arbuckle on 08/08/2017.
 *
 */
@Module
public class LoginModule {

  @AuthScope
  @Provides
  LoginPresenterImpl providesPresenter(FirebaseAuthHelper authHelper) {
    return new LoginPresenterImpl(authHelper);
  }

}

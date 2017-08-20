package com.ianarbuckle.dublinbushelper.authentication.dagger;

import com.ianarbuckle.dublinbushelper.ApplicationComponent;
import com.ianarbuckle.dublinbushelper.authentication.login.LoginFragment;
import com.ianarbuckle.dublinbushelper.authentication.register.RegisterFragment;

import dagger.Component;

/**
 * Created by Ian Arbuckle on 08/08/2017.
 *
 */
@AuthScope
@Component(modules = {LoginModule.class, RegisterModule.class}, dependencies = ApplicationComponent.class)
public interface AuthComponent {
  void inject(LoginFragment fragment);
  void inject(RegisterFragment fragment);
}

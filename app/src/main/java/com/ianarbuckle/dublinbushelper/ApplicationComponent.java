package com.ianarbuckle.dublinbushelper;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Ian Arbuckle on 20/02/2017.
 *
 */
@Component(modules = {ApplicationModule.class})
@Singleton
public interface ApplicationComponent {
  Context context();
}

package com.ianarbuckle.dublinbushelper.transports.irishrail.di;

import com.ianarbuckle.dublinbushelper.ApplicationComponent;
import com.ianarbuckle.dublinbushelper.transports.irishrail.RailFragment;

import dagger.Component;

/**
 * Created by Ian Arbuckle on 12/08/2017.
 *
 */
@RailScope
@Component(modules = RailModule.class, dependencies = ApplicationComponent.class)
public interface RailComponent {
  void inject(RailFragment fragment);
}

package com.ianarbuckle.dublinbushelper.transports.luas.di;

import com.ianarbuckle.dublinbushelper.ApplicationComponent;
import com.ianarbuckle.dublinbushelper.transports.luas.LuasFragment;

import dagger.Component;

/**
 * Created by Ian Arbuckle on 12/08/2017.
 *
 */
@LuasScope
@Component(modules = LuasModule.class, dependencies = ApplicationComponent.class)
public interface LuasComponent {
  void inject(LuasFragment fragment);
}

package com.ianarbuckle.dublinbushelper.transports.dublinbus.dagger;

import com.ianarbuckle.dublinbushelper.ApplicationComponent;
import com.ianarbuckle.dublinbushelper.transports.dublinbus.DublinBusFragment;
import com.ianarbuckle.dublinbushelper.transports.dublinbus.PopupDialogFragment;

import dagger.Component;

/**
 * Created by Ian Arbuckle on 12/08/2017.
 *
 */
@DublinBusScope
@Component(modules = {DublinBusModule.class}, dependencies = ApplicationComponent.class)
public interface DublinBusComponent {
  void inject(DublinBusFragment fragment);
  void inject(PopupDialogFragment dialogFragment);
}

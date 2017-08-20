package com.ianarbuckle.dublinbushelper.transports.schedules.dagger;

import com.ianarbuckle.dublinbushelper.ApplicationComponent;
import com.ianarbuckle.dublinbushelper.transports.schedules.ScheduleFragment;

import dagger.Component;

/**
 * Created by Ian Arbuckle on 12/08/2017.
 *
 */
@ScheduleScope
@Component(modules = ScheduleModule.class, dependencies = ApplicationComponent.class)
public interface ScheduleComponent {
  void inject(ScheduleFragment fragment);
}

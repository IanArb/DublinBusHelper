package com.ianarbuckle.dublinbushelper.search.searchlist.stop.ui.builder;

import com.ianarbuckle.dublinbushelper.ApplicationComponent;
import com.ianarbuckle.dublinbushelper.search.searchlist.StopSearchListActivity;

import dagger.Component;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */
@StopSearchListScope
@Component(modules = {StopSearchListModule.class}, dependencies = ApplicationComponent.class)
public interface StopSearchListComponent {
  void inject(StopSearchListActivity activity);
}

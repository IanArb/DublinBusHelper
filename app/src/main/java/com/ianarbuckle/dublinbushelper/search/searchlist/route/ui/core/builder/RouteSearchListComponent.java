package com.ianarbuckle.dublinbushelper.search.searchlist.route.ui.core.builder;

import com.ianarbuckle.dublinbushelper.ApplicationComponent;
import com.ianarbuckle.dublinbushelper.search.searchlist.route.RouteSearchListActivity;

import dagger.Component;

/**
 * Created by Ian Arbuckle on 30/10/2017.
 *
 */
@RouteSearchListScope
@Component(modules = RouteSearchListModule.class, dependencies = ApplicationComponent.class)
public interface RouteSearchListComponent {
  void inject(RouteSearchListActivity activity);
}

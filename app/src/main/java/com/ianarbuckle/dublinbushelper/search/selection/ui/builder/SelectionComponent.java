package com.ianarbuckle.dublinbushelper.search.selection.ui.builder;

import com.ianarbuckle.dublinbushelper.ApplicationComponent;
import com.ianarbuckle.dublinbushelper.search.selection.SelectionActivity;

import dagger.Component;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */
@SelectionScope
@Component(modules = SelectionModule.class, dependencies = ApplicationComponent.class)
public interface SelectionComponent {
  void inject(SelectionActivity activity);
}

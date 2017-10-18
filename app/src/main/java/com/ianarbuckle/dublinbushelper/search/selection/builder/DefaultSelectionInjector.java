package com.ianarbuckle.dublinbushelper.search.selection.builder;

import com.ianarbuckle.dublinbushelper.TransportHelperApplication;
import com.ianarbuckle.dublinbushelper.search.selection.SelectionActivity;
import com.ianarbuckle.dublinbushelper.search.selection.ui.SelectionView;
import com.ianarbuckle.dublinbushelper.search.selection.ui.builder.DaggerSelectionComponent;
import com.ianarbuckle.dublinbushelper.search.selection.ui.builder.SelectionModule;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */

public class DefaultSelectionInjector implements SelectionInjector {

  private final SelectionView view;

  public DefaultSelectionInjector(SelectionView view) {
    this.view = view;
  }

  @Override
  public void inject(SelectionActivity activity) {
    DaggerSelectionComponent.builder()
        .applicationComponent(TransportHelperApplication.get(activity).getApplicationComponent())
        .selectionModule(new SelectionModule(view))
        .build()
        .inject(activity);
  }
}

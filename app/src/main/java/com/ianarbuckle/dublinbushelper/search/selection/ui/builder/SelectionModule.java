package com.ianarbuckle.dublinbushelper.search.selection.ui.builder;

import com.ianarbuckle.dublinbushelper.network.RealTimePassengerInfoAPI;
import com.ianarbuckle.dublinbushelper.search.selection.ui.SelectionView;
import com.ianarbuckle.dublinbushelper.search.selection.ui.core.interactor.DefaultSelectionInteractor;
import com.ianarbuckle.dublinbushelper.search.selection.ui.core.interactor.SelectionInteractor;
import com.ianarbuckle.dublinbushelper.search.selection.ui.core.presenter.DefaultSelectionPresenter;
import com.ianarbuckle.dublinbushelper.search.selection.ui.core.presenter.SelectionPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */
@Module
public class SelectionModule {

  private final SelectionView view;

  public SelectionModule(SelectionView view) {
    this.view = view;
  }

  @SelectionScope
  @Provides
  SelectionInteractor providesDefaultInteractor(RealTimePassengerInfoAPI realTimePassengerInfoAPI) {
    return new DefaultSelectionInteractor(realTimePassengerInfoAPI);
  }

  @SelectionScope
  @Provides
  SelectionPresenter providesDefaultPresenter(SelectionInteractor interactor) {
    return new DefaultSelectionPresenter(view, interactor);
  }

}

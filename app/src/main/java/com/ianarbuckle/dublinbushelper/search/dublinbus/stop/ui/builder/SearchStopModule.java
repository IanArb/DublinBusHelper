package com.ianarbuckle.dublinbushelper.search.dublinbus.stop.ui.builder;

import android.content.Context;

import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.ui.core.view.SearchStopView;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.SearchStopFragment;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.ui.core.view.DefaultSearchStopView;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.ui.core.presenter.DefaultSearchStopPresenter;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.ui.core.presenter.SearchStopPresenter;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.wireframe.DefaultSearchStopWireframe;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.wireframe.SearchStopWireframe;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */
@SearchBusStopScope
@Module
public class SearchStopModule {

  private final SearchStopFragment fragment;

  public SearchStopModule(SearchStopFragment fragment) {
    this.fragment = fragment;
  }

  @SearchBusStopScope
  @Provides
  SearchStopView providesView(Context context) {
    return new DefaultSearchStopView(context);
  }

  @SearchBusStopScope
  @Provides
  SearchStopWireframe providesWireframe() {
    return new DefaultSearchStopWireframe(fragment);
  }

  @SearchBusStopScope
  @Provides
  SearchStopPresenter providesPresenter(SearchStopView view, SearchStopWireframe wireframe) {
    return new DefaultSearchStopPresenter(view, wireframe);
  }
}

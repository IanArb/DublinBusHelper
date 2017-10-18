package com.ianarbuckle.dublinbushelper.search.dublinbus.stop.ui.dagger;

import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.SearchStopFragment;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.ui.DefaultSearchStopView;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.ui.SearchStopView;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.ui.presenter.DefaultSearchStopPresenter;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.ui.presenter.SearchStopPresenter;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.wireframe.DefaultSearchStopWireframe;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.wireframe.SearchStopWireframe;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */
@Module
public class SearchStopModule {

  private final SearchStopFragment fragment;

  public SearchStopModule(SearchStopFragment fragment) {
    this.fragment = fragment;
  }

  @SearchBusStopScope
  @Provides
  SearchStopView providesView() {
    return new DefaultSearchStopView(fragment.getContext());
  }

  @SearchBusStopScope
  @Provides
  SearchStopPresenter providesPresenter(SearchStopView view, SearchStopWireframe wireframe) {
    return new DefaultSearchStopPresenter(view, wireframe);
  }

  @SearchBusStopScope
  @Provides
  SearchStopWireframe providesWireframe() {
    return new DefaultSearchStopWireframe(fragment);
  }
}

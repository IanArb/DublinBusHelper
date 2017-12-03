package com.ianarbuckle.dublinbushelper.search.dublinbus.route.ui.builder;

import android.content.Context;

import com.ianarbuckle.dublinbushelper.search.dublinbus.route.SearchRouteFragment;
import com.ianarbuckle.dublinbushelper.search.dublinbus.route.ui.core.view.DefaultSearchRouteView;
import com.ianarbuckle.dublinbushelper.search.dublinbus.route.ui.core.view.SearchRouteView;
import com.ianarbuckle.dublinbushelper.search.dublinbus.route.ui.core.presenter.DefaultSearchRoutePresenter;
import com.ianarbuckle.dublinbushelper.search.dublinbus.route.ui.core.presenter.SearchRoutePresenter;
import com.ianarbuckle.dublinbushelper.search.dublinbus.route.wireframe.DefaultSearchRouteWireframe;
import com.ianarbuckle.dublinbushelper.search.dublinbus.route.wireframe.SearchRouteWireframe;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */
@SearchBusRouteScope
@Module
public class SearchBusRouteModule {
  private final SearchRouteFragment fragment;

  public SearchBusRouteModule(SearchRouteFragment fragment) {
    this.fragment = fragment;
  }

  @Provides
  SearchRouteView providesRouteView(Context context) {
    return new DefaultSearchRouteView(context);
  }

  @Provides
  SearchRouteWireframe providesWireframe() {
    return new DefaultSearchRouteWireframe(fragment);
  }

  @Provides
  SearchRoutePresenter providesPresenter(SearchRouteView view, SearchRouteWireframe wireframe) {
    return new DefaultSearchRoutePresenter(view, wireframe);
  }
}

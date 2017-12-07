package com.ianarbuckle.dublinbushelper.search.dublinbus.route;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ianarbuckle.dublinbushelper.BaseFragment;
import com.ianarbuckle.dublinbushelper.search.dublinbus.route.ui.core.view.SearchRouteView;
import com.ianarbuckle.dublinbushelper.search.dublinbus.route.ui.core.presenter.SearchRoutePresenter;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.builder.DefaultSearchStopInjector;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.builder.SearchStopInjector;

import javax.inject.Inject;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */

public class SearchRouteFragment extends BaseFragment {

  @Inject
  SearchRouteView view;

  @Inject
  SearchRoutePresenter presenter;

  public static SearchRouteFragment newInstance() {
    return new SearchRouteFragment();
  }

  @Override
  protected void injectDagger() {
    SearchStopInjector injector = new DefaultSearchStopInjector();
    injector.inject(this);
  }

  @Override
  public void onStart() {
    super.onStart();
    presenter.onCreate();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    presenter.onDestroy();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return view.getView();
  }
}

package com.ianarbuckle.dublinbushelper.search.dublinbus.route;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ianarbuckle.dublinbushelper.BaseFragment;
import com.ianarbuckle.dublinbushelper.search.dublinbus.route.builder.DefaultSearchBusRouteInjector;
import com.ianarbuckle.dublinbushelper.search.dublinbus.route.builder.SearchBusRouteInjector;
import com.ianarbuckle.dublinbushelper.search.dublinbus.route.ui.SearchRouteView;

import javax.inject.Inject;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */

public class SearchRouteFragment extends BaseFragment {

  @Inject
  SearchRouteView view;

  public static SearchRouteFragment newInstance() {
    return new SearchRouteFragment();
  }

  @Override
  protected void injectDagger() {
    SearchBusRouteInjector injector = new DefaultSearchBusRouteInjector();
    injector.inject(this);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return view.getView();
  }
}

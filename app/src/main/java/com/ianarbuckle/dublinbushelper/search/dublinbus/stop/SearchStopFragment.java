package com.ianarbuckle.dublinbushelper.search.dublinbus.stop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ianarbuckle.dublinbushelper.BaseFragment;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.ui.core.view.SearchStopView;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.builder.DefaultSearchStopInjector;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.builder.SearchStopInjector;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.ui.core.presenter.SearchStopPresenter;
import com.ianarbuckle.dublinbushelper.utils.Constants;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */

public class SearchStopFragment extends BaseFragment {

  @Inject
  SearchStopView view;

  @Inject
  SearchStopPresenter presenter;

  public static SearchStopFragment newInstance() {
    return new SearchStopFragment();
  }

  @Override
  protected void injectDagger() {
    SearchStopInjector injector = new DefaultSearchStopInjector();
    injector.inject(this);
  }

  @Override
  public void onStart() {
    super.onStart();
    presenter.onStart();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    presenter.onDestroy();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK) {
      view.setCardValues(data);
    }
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return view.getView();
  }
}

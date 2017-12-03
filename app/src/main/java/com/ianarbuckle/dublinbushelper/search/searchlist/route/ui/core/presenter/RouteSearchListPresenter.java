package com.ianarbuckle.dublinbushelper.search.searchlist.route.ui.core.presenter;

import com.ianarbuckle.dublinbushelper.search.searchlist.common.BaseSearchPresenter;

/**
 * Created by Ian Arbuckle on 30/10/2017.
 *
 */

public interface RouteSearchListPresenter extends BaseSearchPresenter {
  @Override
  void onCreate();

  @Override
  void onDestroy();
}

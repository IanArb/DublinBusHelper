package com.ianarbuckle.dublinbushelper.search.searchlist.stop.ui.core.presenter;

import com.ianarbuckle.dublinbushelper.search.searchlist.common.BaseSearchPresenter;

/**
 * Created by Ian Arbuckle on 30/10/2017.
 *
 */

public interface StopSearchPresenter extends BaseSearchPresenter {
  @Override
  void onCreate();

  @Override
  void onDestroy();
}

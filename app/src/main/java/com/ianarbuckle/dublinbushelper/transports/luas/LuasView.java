package com.ianarbuckle.dublinbushelper.transports.luas;

import android.content.Context;

/**
 * Created by Ian Arbuckle on 02/03/2017.
 *
 */

public interface LuasView {
  Context getContext();
  void showProgress();
  void hideProgress();
  void showErrorMessage();
  void showSuccessMessage();
  String setFilter();
}

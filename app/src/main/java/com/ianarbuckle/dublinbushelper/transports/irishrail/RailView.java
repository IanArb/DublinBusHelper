package com.ianarbuckle.dublinbushelper.transports.irishrail;

import android.content.Context;

/**
 * Created by Ian Arbuckle on 29/03/2017.
 *
 */

public interface RailView {
  Context getContext();
  void showProgress();
  void hideProgress();
  void showErrorMessage();
  void showSuccessMessage();
  String setFilter();
}

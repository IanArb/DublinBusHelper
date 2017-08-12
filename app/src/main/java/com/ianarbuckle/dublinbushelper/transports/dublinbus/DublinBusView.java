package com.ianarbuckle.dublinbushelper.transports.dublinbus;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Ian Arbuckle on 19/02/2017.
 *
 */

public interface DublinBusView {
  Activity getActivity();
  Context getContext();
  void showProgress();
  void hideProgress();
  void showErrorMessage();
  void showPopupFragment(String displayStopId, String shortName, String shortNameLocalised, String lastUpdate, String routes, float latitude, float longtitude);
  void setLocalVisibility();
}

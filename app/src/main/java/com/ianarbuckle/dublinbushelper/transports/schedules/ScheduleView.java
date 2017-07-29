package com.ianarbuckle.dublinbushelper.transports.schedules;

import android.content.Context;

/**
 * Created by Ian Arbuckle on 31/03/2017.
 *
 */

public interface ScheduleView {
  Context getContext();
  void showProgress();
  void hideProgress();
  void showErrorMessage();
}

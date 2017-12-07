package com.ianarbuckle.dublinbushelper.search.dublinbus.stop.ui.core.view;

import android.content.Intent;
import android.view.View;

import rx.Observable;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */

public interface SearchStopView {
  View getView();
  Observable<Void> getCardClicks();
  Observable<Void> getContinueClicks();
  void setCardValues(Intent intent);
}

package com.ianarbuckle.dublinbushelper.search.dublinbus.route.ui.core.view;

import android.view.View;

import rx.Observable;

/**
 * Created by Ian Arbuckle on 04/11/2017.
 *
 */

public interface SearchRouteView {
  View getView();
  Observable<Boolean> observeChangeFocusField();
  Observable<String> getObservableRouteId();
  Observable<Void> getContinueClicksObservable();
  void showError(String reason);
  void enableContinueButton();
  String getFieldInput();
}

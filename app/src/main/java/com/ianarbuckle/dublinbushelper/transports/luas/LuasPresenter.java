package com.ianarbuckle.dublinbushelper.transports.luas;

import com.ianarbuckle.dublinbushelper.models.stopinfo.Result;

import java.util.List;

/**
 * Created by Ian Arbuckle on 02/03/2017.
 *
 */

public interface LuasPresenter {
  void fetchStops();
  void sendToDatabase(Result result);
  void onBindRowViewAtPositon(int position, LuasCardRowView view);
  int getResultsRowCount();
  List<Result> getResultsList();
  void onRowClickAtPosition(int position, LuasCardRowView luasView);
  Result getResults(int position);
  void setRouteText(int position, LuasCardRowView luasView);
  void onStop();
}

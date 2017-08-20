package com.ianarbuckle.dublinbushelper.transports.irishrail;

import com.ianarbuckle.dublinbushelper.models.stopinfo.Result;

import java.util.List;

/**
 * Created by Ian Arbuckle on 29/03/2017.
 *
 */

public interface RailPresenter {
  void fetchStations();
  void sendToDatabase(Result result);
  void onBindRowViewAtPositon(int position, RailCardRowView view);
  int getResultsRowCount();
  List<Result> getResultsList();
  void onRowClickAtPosition(int position, RailCardRowView view);
  Result getResults(int position);
  void setRouteText(int position, RailCardRowView view);
  void onStop();
}

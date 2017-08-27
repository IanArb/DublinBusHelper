package com.ianarbuckle.dublinbushelper.transports.schedules;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Ian Arbuckle on 31/03/2017.
 *
 */

public interface SchedulePresenter {
  void fetchSchedules(String stopId);
  void onRequestPermission();
  void initMap(GoogleMap googleMap);
  void onBindRowViewAtPosition(int position, ScheduleRowView view);
  int getResultsRowCount();
  void onStop();
}

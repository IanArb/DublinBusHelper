package com.ianarbuckle.dublinbushelper.transports.dublinbus;

import android.support.v4.app.Fragment;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Ian Arbuckle on 19/02/2017.
 *
 */

public interface DublinBusPresenter {
  boolean checkLocationPermission(Fragment fragment);
  void onRequestPermission();
  void initMap(GoogleMap googleMap);
  void fetchStops();
  void sendToDatabase(String shortName, float latitude, float longtitude, String lastUpdated, String displayStopId, String routes);
}

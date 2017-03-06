package com.ianarbuckle.dublinbushelper.dublinbus;

import android.support.v4.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.ianarbuckle.dublinbushelper.models.MarkerItemModel;
import com.ianarbuckle.dublinbushelper.models.busstopinfo.Result;

/**
 * Created by Ian Arbuckle on 19/02/2017.
 *
 */

public interface DublinBusPresenter {
  boolean checkLocationPermission(Fragment fragment);
  void onRequestPermission();
  void initMap(GoogleMap googleMap);
  void fetchStops();
  MarkerItemModel getMarkerItems(Result result);
}

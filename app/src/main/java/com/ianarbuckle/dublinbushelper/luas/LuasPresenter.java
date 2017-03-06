package com.ianarbuckle.dublinbushelper.luas;

import android.support.v4.app.Fragment;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Ian Arbuckle on 02/03/2017.
 *
 */

public interface LuasPresenter {
  boolean checkLocationPermission(Fragment fragment);
  void onRequestPermission();
  void initMap(GoogleMap googleMap);
}

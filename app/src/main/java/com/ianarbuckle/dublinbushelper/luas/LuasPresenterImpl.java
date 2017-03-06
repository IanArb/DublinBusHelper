package com.ianarbuckle.dublinbushelper.luas;

import android.support.v4.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.ianarbuckle.dublinbushelper.helper.LocationHelper;
import com.ianarbuckle.dublinbushelper.helper.LocationHelperImpl;

/**
 * Created by Ian Arbuckle on 02/03/2017.
 *
 */

public class LuasPresenterImpl implements LuasPresenter {

  LuasView view;

  private LocationHelper locationHelper;

  public LuasPresenterImpl(LuasView view) {
    this.view = view;
    locationHelper = new LocationHelperImpl(view.getContext());
  }

  @Override
  public boolean checkLocationPermission(Fragment fragment) {
    return locationHelper.checkLocationPermission(fragment);
  }

  @Override
  public void initMap(GoogleMap googleMap) {
    locationHelper.initMap(googleMap);
  }

  @Override
  public void onRequestPermission() {
    locationHelper.onRequestPermission();
  }

}

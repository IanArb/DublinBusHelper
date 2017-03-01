package com.ianarbuckle.dublinbushelper.map;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.ianarbuckle.dublinbushelper.BaseFragment;
import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.utils.Constants;
import com.ianarbuckle.dublinbushelper.utils.ErrorDialogFragment;

/**
 * Created by Ian Arbuckle on 19/02/2017.
 *
 */

public class BusMapFragment extends BaseFragment implements BusMapView {

  BusMapPresenterImpl presenter;

  public static Fragment newInstance() {
    return new BusMapFragment();
  }


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_bus_map, container, false);
  }

  @Override
  protected void initPresenter() {
    presenter = new BusMapPresenterImpl(this);
  }

  @Override
  public void onStart() {
    super.onStart();
    presenter.checkLocationPermission(this);
    initMap();
  }

  private void initMap() {
    SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager()
        .findFragmentById(R.id.fragment_map);

    supportMapFragment = initFragment(supportMapFragment);

    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(GoogleMap googleMap) {
        presenter.initMap(googleMap);
      }
    });
  }

  private SupportMapFragment initFragment(SupportMapFragment supportMapFragment) {
    if (supportMapFragment == null) {
      FragmentManager fragmentManager = getFragmentManager();
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      supportMapFragment = SupportMapFragment.newInstance();
      fragmentTransaction.replace(R.id.fragment_map, supportMapFragment).commit();
    }
    return supportMapFragment;
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch (requestCode) {
      case Constants.PERMISSION_REQUEST_ACCESS_LOCATION: {
        if (grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          presenter.onRequestPermission();
        }
      }
    }
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }



  private void showErrorPermissionsDialog() {
    FragmentTransaction fragmentTransaction = initFragmentManager();
    DialogFragment errorDialog = ErrorDialogFragment.newInstance(R.string.common_permission_required);
    fragmentTransaction.add(errorDialog, Constants.ERROR_DIALOG_FRAGMENT);
    errorDialog.show(fragmentTransaction, Constants.ERROR_DIALOG_FRAGMENT);
  }

  @NonNull
  private FragmentTransaction initFragmentManager() {
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    Fragment fragment = getFragmentManager().findFragmentByTag(Constants.ERROR_DIALOG_FRAGMENT);

    if (fragment != null && fragment.isAdded()) {
      fragmentTransaction.remove(fragment);
    }

    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commitAllowingStateLoss();
    return fragmentTransaction;
  }

}

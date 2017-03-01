package com.ianarbuckle.dublinbushelper.dublinbus;

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
import com.ianarbuckle.dublinbushelper.utils.PopupDialogFragment;


/**
 * Created by Ian Arbuckle on 19/02/2017.
 *
 */

public class DublinBusDublinBusFragment extends BaseFragment implements DublinBusView {

  DublinBusPresenterImpl presenter;

  public static Fragment newInstance() {
    return new DublinBusDublinBusFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_map, container, false);
  }

  @Override
  protected void initPresenter() {
    presenter = new DublinBusPresenterImpl(this);
  }

  @Override
  public void onStart() {
    super.onStart();
    presenter.checkLocationPermission(this);
    presenter.fetchStops();
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
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          presenter.onRequestPermission();
        }
      }
    }
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  @Override
  public void showProgress() {
    showProgressDialog();
  }

  @Override
  public void hideProgress() {
    hideProgressDialog();
  }

  @Override
  public void showErrorMessage() {
    FragmentTransaction fragmentTransaction = getFragmentTransaction();
    DialogFragment dialogFragment = ErrorDialogFragment.newInstance(R.string.error_dialog_title);
    dialogFragment.show(fragmentTransaction, Constants.DIALOG_FRAGMENT);
  }

  @Override
  public void showPopupFragment(String displayStopId, String shortName, String shortNameLocalised, String lastUpdate, String routes) {
    FragmentTransaction fragmentTransaction = getFragmentTransaction();
    DialogFragment dialogFragment = PopupDialogFragment.newInstance(displayStopId, shortName, shortNameLocalised, lastUpdate, routes);
    dialogFragment.onCreateAnimation(R.anim.slide_up, true, R.anim.slide_down);
    dialogFragment.show(fragmentTransaction, Constants.POPUP_FRAGMENT);
  }

  @NonNull
  private FragmentTransaction getFragmentTransaction() {
    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
    Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(Constants.DIALOG_FRAGMENT);

    if (fragment != null) {
      fragmentTransaction.remove(fragment);
    }

    fragmentTransaction.addToBackStack(null);
    return fragmentTransaction;
  }
}

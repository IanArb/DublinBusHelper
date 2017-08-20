package com.ianarbuckle.dublinbushelper.transports.dublinbus;

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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.ianarbuckle.dublinbushelper.BaseFragment;
import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.TransportHelperApplication;
import com.ianarbuckle.dublinbushelper.transports.dublinbus.dagger.DaggerDublinBusComponent;
import com.ianarbuckle.dublinbushelper.transports.dublinbus.dagger.DublinBusModule;
import com.ianarbuckle.dublinbushelper.utils.Constants;
import com.ianarbuckle.dublinbushelper.utils.ErrorDialogFragment;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;


/**
 * Created by Ian Arbuckle on 19/02/2017.
 *
 */

public class DublinBusFragment extends BaseFragment implements DublinBusView {

  @Nullable
  @BindView(R.id.tvShortnameLocal)
  TextView tvShortnameLocal;

  @BindView(R.id.rlProgress)
  RelativeLayout rlProgress;

  @BindView(R.id.progressBar)
  ProgressBar progressBar;

  @Inject @Named("fragmentPresenter")
  DublinBusPresenterImpl presenter;

  public static Fragment newInstance() {
    return new DublinBusFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_map, container, false);
  }

  @Override
  protected void injectDagger() {
    DaggerDublinBusComponent.builder()
        .dublinBusModule(new DublinBusModule(this))
        .applicationComponent(TransportHelperApplication.get(this).getApplicationComponent())
        .build()
        .inject(this);
  }

  @Override
  public void onStart() {
    super.onStart();
    presenter.checkLocationPermission(this);
    initMap();
    presenter.fetchStops();
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
    if(progressBar != null) {
      progressBar.setProgress(100);
    }
  }

  @Override
  public void hideProgress() {
    if(progressBar != null) {
      rlProgress.setVisibility(View.GONE);
      progressBar.setVisibility(View.GONE);
    }
  }

  @Override
  public void showErrorMessage() {
    FragmentTransaction fragmentTransaction = getFragmentTransaction();
    DialogFragment dialogFragment = ErrorDialogFragment.newInstance(R.string.error_dialog_title);
    dialogFragment.show(fragmentTransaction, Constants.DIALOG_FRAGMENT);
  }

  @Override
  public void showPopupFragment(String displayStopId, String shortName, String shortNameLocalised, String lastUpdate, String routes, float lat, float lon) {
    FragmentTransaction fragmentTransaction = getFragmentTransaction();
    DialogFragment dialogFragment = PopupDialogFragment.newInstance(displayStopId, shortName, shortNameLocalised, lastUpdate, routes, lat, lon);
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

  @Override
  public void setLocalVisibility() {
    assert tvShortnameLocal != null;
    tvShortnameLocal.setVisibility(View.VISIBLE);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    presenter.onStop();
  }
}

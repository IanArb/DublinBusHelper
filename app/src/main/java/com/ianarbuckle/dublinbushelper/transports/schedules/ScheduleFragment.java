package com.ianarbuckle.dublinbushelper.transports.schedules;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.ianarbuckle.dublinbushelper.BaseFragment;
import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.TransportHelperApplication;
import com.ianarbuckle.dublinbushelper.transports.schedules.dagger.DaggerScheduleComponent;
import com.ianarbuckle.dublinbushelper.transports.schedules.dagger.ScheduleModule;
import com.ianarbuckle.dublinbushelper.utils.Constants;
import com.ianarbuckle.dublinbushelper.utils.ErrorDialogFragment;

import javax.inject.Inject;

import butterknife.BindView;


/**
 * Created by Ian Arbuckle on 02/03/2017.
 *
 */

public class ScheduleFragment extends BaseFragment implements ScheduleView {

  @BindView(R.id.rv)
  RecyclerView recyclerView;

  ScheduleAdapter adapter;

  @BindView(R.id.rlProgress)
  RelativeLayout rlProgress;

  @BindView(R.id.progressBar)
  ProgressBar progressBar;

  LinearLayoutManager linearLayoutManager;

  @Inject
  SchedulePresenterImpl presenter;

  Marker stationLocation;

  public static Fragment newInstance() {
    return new ScheduleFragment();
  }

  @Override
  protected void injectDagger() {
    DaggerScheduleComponent.builder()
        .scheduleModule(new ScheduleModule(this))
        .applicationComponent(TransportHelperApplication.get(this).getApplicationComponent())
        .build()
        .inject(this);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_schedule, container, false);
  }

  @Override
  public void onStart() {
    super.onStart();
    Intent intent = getActivity().getIntent();
    String stopId = intent.getStringExtra(Constants.STOPID_KEY);
    presenter.fetchSchedules(stopId);
    attachRecyclerView();
    initMap();
  }

  private void attachRecyclerView() {
    recyclerView.setHasFixedSize(true);
    linearLayoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(linearLayoutManager);
    adapter = new ScheduleAdapter(presenter);
    recyclerView.setAdapter(adapter);
  }

  private void initMap() {
    SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager()
        .findFragmentById(R.id.fragment_map);

    supportMapFragment = initFragment(supportMapFragment);

    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(GoogleMap googleMap) {
        setMarker(googleMap);
        setGeneratedIcon();
      }
    });
  }

  private void setMarker(GoogleMap googleMap) {
    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    googleMap.getUiSettings().setMapToolbarEnabled(false);

    Intent intent = getActivity().getIntent();
    float latitude = intent.getFloatExtra(Constants.LAT_KEY, 0);
    float longtitude = intent.getFloatExtra(Constants.LONG_KEY, 0);

    LatLng latLng = new LatLng(latitude, longtitude);

    stationLocation = googleMap.addMarker(new MarkerOptions().position(latLng));

    googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(latLng, 16)));
  }

  private void setGeneratedIcon() {
    IconGenerator iconGenerator = new IconGenerator(getContext());

    iconGenerator.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_my_location));

    BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon());
    stationLocation.setIcon(icon);
  }

  private SupportMapFragment initFragment(SupportMapFragment supportMapFragment) {
    if (supportMapFragment == null) {
      FragmentManager fragmentManager = getFragmentManager();
      supportMapFragment = new SupportMapFragment();
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      fragmentTransaction.replace(R.id.fragment_map, supportMapFragment).commit();
      setRetainInstance(true);
    }
    return supportMapFragment;
  }

  @Override
  public void showProgress() {
    if(progressBar != null) {
      progressBar.setProgress(Constants.PROGRESS_BAR_VALUE);
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
    FragmentManager fragmentManager = getFragmentManager();
    DialogFragment dialogFragment = ErrorDialogFragment.newInstance(R.string.error_dialog_title);
    dialogFragment.show(fragmentTransaction, Constants.DIALOG_FRAGMENT);
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
  public void onStop() {
    super.onStop();
    presenter.onStop();
  }
}

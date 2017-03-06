package com.ianarbuckle.dublinbushelper.luas.luasmap;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
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
import com.ianarbuckle.dublinbushelper.luas.LuasPresenterImpl;
import com.ianarbuckle.dublinbushelper.luas.LuasView;
import com.ianarbuckle.dublinbushelper.utils.Constants;

import butterknife.BindView;

/**
 * Created by Ian Arbuckle on 02/03/2017.
 *
 */

public class LuasMapFragment extends BaseFragment implements LuasView {

  LuasPresenterImpl presenter;

  @BindView(R.id.tabs)
  TabLayout tabLayout;

  public static Fragment newInstance() {
    return new LuasMapFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_map, container, false);
  }

  @Override
  protected void initPresenter() {
    presenter = new LuasPresenterImpl(this);
  }

  @Override
  public void onStart() {
    super.onStart();
    presenter.checkLocationPermission(this);
    initMap();
    initTabLayout();
  }

  private void initTabLayout() {
    assert tabLayout != null;
    tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_title_dublin_bus));
    tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_title_luas));
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
    if(supportMapFragment == null) {
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
}

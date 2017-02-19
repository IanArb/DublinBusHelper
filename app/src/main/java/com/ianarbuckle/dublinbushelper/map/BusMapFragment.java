package com.ianarbuckle.dublinbushelper.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ianarbuckle.dublinbushelper.BaseFragment;
import com.ianarbuckle.dublinbushelper.R;

/**
 * Created by Ian Arbuckle on 19/02/2017.
 *
 */

public class BusMapFragment extends BaseFragment {

  public static Fragment newInstance() {
    return new BusMapFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_map, container, false);
  }

  @Override
  protected void initPresenter() {

  }
}

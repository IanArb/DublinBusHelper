package com.ianarbuckle.dublinbushelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ian Arbuckle on 14/02/2017.
 *
 */

public class BlankFragment extends BaseFragment {

  public static Fragment newInstance() {
    return new BlankFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.activity_blank, container, false);
  }

  @Override
  protected void initPresenter() {

  }
}

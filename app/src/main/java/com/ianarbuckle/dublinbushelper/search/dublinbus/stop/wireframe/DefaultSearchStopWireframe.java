package com.ianarbuckle.dublinbushelper.search.dublinbus.stop.wireframe;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.ianarbuckle.dublinbushelper.search.searchlist.StopSearchListActivity;

/**
 * Created by Ian Arbuckle on 06/10/2017.
 *
 */

public class DefaultSearchStopWireframe implements SearchStopWireframe {
  private final Fragment fragment;

  public DefaultSearchStopWireframe(Fragment fragment) {
    this.fragment = fragment;
  }

  @Override
  public void onClickCard() {
    StopSearchListActivity.init(fragment.getActivity());
  }

  @Override
  public void onStopClickContinue() {
    Toast.makeText(fragment.getContext(), "Clicked", Toast.LENGTH_SHORT).show();
  }
}

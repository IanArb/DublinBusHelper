package com.ianarbuckle.dublinbushelper.search.dublinbus.route.wireframe;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.ianarbuckle.dublinbushelper.search.searchlist.route.RouteSearchListActivity;

/**
 * Created by Ian Arbuckle on 19/10/2017.
 *
 */

public class DefaultSearchRouteWireframe implements SearchRouteWireframe {
  private final Fragment fragment;

  public DefaultSearchRouteWireframe(Fragment fragment) {
    this.fragment = fragment;
  }

  @Override
  public void onContinueClick() {
    Toast.makeText(fragment.getContext(), "Test", Toast.LENGTH_SHORT).show();
  }
}

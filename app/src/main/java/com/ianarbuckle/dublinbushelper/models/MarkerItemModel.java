package com.ianarbuckle.dublinbushelper.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Ian Arbuckle on 20/02/2017.
 *
 */

public class MarkerItemModel implements ClusterItem {

  private final LatLng position;
  private final String displayStopId;
  private final String shortName;
  private final String shortNameLocalised;
  private final String lastUpdated;
  private final String routes;

  public MarkerItemModel(LatLng position, String displayStopId, String shortName, String shortNameLocalised, String lastUpdated, String routes) {
    this.position = position;
    this.displayStopId = displayStopId;
    this.shortName = shortName;
    this.shortNameLocalised = shortNameLocalised;
    this.lastUpdated = lastUpdated;
    this.routes = routes;
  }

  @Override
  public LatLng getPosition() {
    return position;
  }

  public String getDisplayStopId() {
    return displayStopId;
  }

  public String getLastUpdated() {
    return lastUpdated;
  }

  public String getShortNameLocalised() {
    return shortNameLocalised;
  }

  public String getShortName() {
    return shortName;
  }

  public String getRoutes() {
    return routes;
  }
}

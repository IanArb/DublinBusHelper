package com.ianarbuckle.dublinbushelper.models.routeinformation;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by Ian Arbuckle on 15/10/2017.
 */

public class Result {
  @Json(name = "operator")
  private String operator;
  @Json(name = "origin")
  private String origin;
  @Json(name = "originlocalized")
  private String originlocalized;
  @Json(name = "destination")
  private String destination;
  @Json(name = "destinationlocalized")
  private String destinationlocalized;
  @Json(name = "lastupdated")
  private String lastupdated;
  @Json(name = "routeStops")
  private List<RouteStop> routeStops = null;

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public String getOrigin() {
    return origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public String getOriginlocalized() {
    return originlocalized;
  }

  public void setOriginlocalized(String originlocalized) {
    this.originlocalized = originlocalized;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getDestinationlocalized() {
    return destinationlocalized;
  }

  public void setDestinationlocalized(String destinationlocalized) {
    this.destinationlocalized = destinationlocalized;
  }

  public String getLastupdated() {
    return lastupdated;
  }

  public void setLastupdated(String lastupdated) {
    this.lastupdated = lastupdated;
  }

  public List<RouteStop> getRouteStops() {
    return routeStops;
  }

  public void setRouteStops(List<RouteStop> routeStops) {
    this.routeStops = routeStops;
  }
}

package com.ianarbuckle.dublinbushelper.models;

/**
 * Created by Ian Arbuckle on 12/08/2017.
 *
 */

public class Favourites {
  private String time;
  private String stopId;
  private String name;
  private String routes;
  private float latitude;
  private float longitude;

  public Favourites() {

  }

  public Favourites(String time, String stopId, String name, String routes, float latitude, float longitude) {
    this.time = time;
    this.stopId = stopId;
    this.name = name;
    this.routes = routes;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getStopId() {
    return stopId;
  }

  public void setStopId(String stopId) {
    this.stopId = stopId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRoutes() {
    return routes;
  }

  public void setRoutes(String routes) {
    this.routes = routes;
  }

  public float getLatitude() {
    return latitude;
  }

  public void setLatitude(float latitude) {
    this.latitude = latitude;
  }

  public float getLongitude() {
    return longitude;
  }

  public void setLongitude(float longitude) {
    this.longitude = longitude;
  }
}

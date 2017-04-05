package com.ianarbuckle.dublinbushelper.models;

/**
 * Created by Ian Arbuckle on 03/04/2017.
 *
 */

public class Favourites {
  private String time;
  private String stopId;
  private String name;
  private String localName;
  private String routes;
  private float lat;
  private float lon;

  public Favourites() {
  }

  public Favourites(String time, String stopId, String name, String routes, float lat, float lon) {
    this.time = time;
    this.stopId = stopId;
    this.name = name;
    this.routes = routes;
    this.lat = lat;
    this.lon = lon;
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

  public String getLocalName() {
    return localName;
  }

  public void setLocalName(String localName) {
    this.localName = localName;
  }

  public String getRoutes() {
    return routes;
  }

  public void setRoutes(String routes) {
    this.routes = routes;
  }

  public float getLat() {
    return lat;
  }

  public void setLat(float lat) {
    this.lat = lat;
  }

  public float getLon() {
    return lon;
  }

  public void setLon(float lon) {
    this.lon = lon;
  }
}

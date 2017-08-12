package com.ianarbuckle.dublinbushelper.transports.dublinbus;

/**
 * Created by Ian Arbuckle on 12/08/2017.
 *
 */

public class ResultsModel {
  private String displayStopId;
  private String shortName;
  private float latitude;
  private float longtitude;

  public ResultsModel(String displayStopId, String shortName, float latitude, float longtitude) {
    this.displayStopId = displayStopId;
    this.shortName = shortName;
    this.latitude = latitude;
    this.longtitude = longtitude;
  }

  public ResultsModel() {
  }

  public String getDisplayStopId() {
    return displayStopId;
  }

  public void setDisplayStopId(String displayStopId) {
    this.displayStopId = displayStopId;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public float getLatitude() {
    return latitude;
  }

  public void setLatitude(float latitude) {
    this.latitude = latitude;
  }

  public float getLongtitude() {
    return longtitude;
  }

  public void setLongtitude(float longtitude) {
    this.longtitude = longtitude;
  }
}

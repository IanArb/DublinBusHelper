package com.ianarbuckle.dublinbushelper.models.routeinformation;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by Ian Arbuckle on 15/10/2017.
 *
 */

public class RouteStop {
  @Json(name = "stopid")
  private String stopid;
  @Json(name = "displaystopid")
  private String displaystopid;
  @Json(name = "shortname")
  private String shortname;
  @Json(name = "shortnamelocalized")
  private String shortnamelocalized;
  @Json(name = "fullname")
  private String fullname;
  @Json(name = "fullnamelocalized")
  private String fullnamelocalized;
  @Json(name = "latitude")
  private String latitude;
  @Json(name = "longitude")
  private String longitude;
  @Json(name = "operators")
  private List<Operator> operators = null;

  public String getStopid() {
    return stopid;
  }

  public void setStopid(String stopid) {
    this.stopid = stopid;
  }

  public String getDisplaystopid() {
    return displaystopid;
  }

  public void setDisplaystopid(String displaystopid) {
    this.displaystopid = displaystopid;
  }

  public String getShortname() {
    return shortname;
  }

  public void setShortname(String shortname) {
    this.shortname = shortname;
  }

  public String getShortnamelocalized() {
    return shortnamelocalized;
  }

  public void setShortnamelocalized(String shortnamelocalized) {
    this.shortnamelocalized = shortnamelocalized;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public String getFullnamelocalized() {
    return fullnamelocalized;
  }

  public void setFullnamelocalized(String fullnamelocalized) {
    this.fullnamelocalized = fullnamelocalized;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public List<Operator> getOperators() {
    return operators;
  }

  public void setOperators(List<Operator> operators) {
    this.operators = operators;
  }
}

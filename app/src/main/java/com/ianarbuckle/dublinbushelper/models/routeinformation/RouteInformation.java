package com.ianarbuckle.dublinbushelper.models.routeinformation;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by Ian Arbuckle on 15/10/2017.
 *
 */

public class RouteInformation {
  @Json(name = "errorcode")
  private String errorcode;
  @Json(name = "errormessage")
  private String errormessage;
  @Json(name = "numberofresults")
  private Integer numberofresults;
  @Json(name = "route")
  private String route;
  @Json(name = "timestamp")
  private String timestamp;
  @Json(name = "results")
  private List<Result> results = null;

  public String getErrorcode() {
    return errorcode;
  }

  public void setErrorcode(String errorcode) {
    this.errorcode = errorcode;
  }

  public String getErrormessage() {
    return errormessage;
  }

  public void setErrormessage(String errormessage) {
    this.errormessage = errormessage;
  }

  public Integer getNumberofresults() {
    return numberofresults;
  }

  public void setNumberofresults(Integer numberofresults) {
    this.numberofresults = numberofresults;
  }

  public String getRoute() {
    return route;
  }

  public void setRoute(String route) {
    this.route = route;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public List<Result> getResults() {
    return results;
  }

  public void setResults(List<Result> results) {
    this.results = results;
  }
}

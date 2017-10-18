package com.ianarbuckle.dublinbushelper.models.routelistinformation;

import com.squareup.moshi.Json;

/**
 * Created by Ian Arbuckle on 15/10/2017.
 *
 */

public class RouteListInformation {
  @Json(name = "operator")
  private String operator;
  @Json(name = "route")
  private String route;

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public String getRoute() {
    return route;
  }

  public void setRoute(String route) {
    this.route = route;
  }
}

package com.ianarbuckle.dublinbushelper.models.routeinformation;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by Ian Arbuckle on 15/10/2017.
 */

public class Operator {
  @Json(name = "name")
  private String name;
  @Json(name = "routes")
  private List<String> routes = null;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getRoutes() {
    return routes;
  }

  public void setRoutes(List<String> routes) {
    this.routes = routes;
  }
}

package com.ianarbuckle.dublinbushelper.transports.irishrail;

/**
 * Created by Ian Arbuckle on 29/07/2017.
 *
 */

public interface RailCardRowView {
  void setTime(String time);
  void setStopId(String stopId);
  void setName(String name);
  void setRoute(String route);
  void setCommuterText(String text);
  void setIntercityText(String text);
  void setDartCommuterText(String text);
  void setInterDartCommuter(String text);
  void setCommuterInter(String text);
}

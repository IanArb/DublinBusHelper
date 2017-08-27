package com.ianarbuckle.dublinbushelper.transports.luas;

/**
 * Created by Ian Arbuckle on 28/07/2017.
 *
 */

public interface LuasCardRowView {
  void setStopId(String stopId);
  void setName(String name);
  void setRoute(String route);
  void setRedColorText(int color);
  void setGreenColorText(int color);
  void setRedText(String redText);
  void setGreenText(String greenText);
}

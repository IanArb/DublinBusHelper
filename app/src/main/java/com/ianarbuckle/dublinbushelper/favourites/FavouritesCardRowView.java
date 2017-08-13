package com.ianarbuckle.dublinbushelper.favourites;

/**
 * Created by Ian Arbuckle on 30/07/2017.
 *
 */

public interface FavouritesCardRowView {
  void setTime(String time);
  void setStopId(String stopId);
  void setRoute(String route);
  void setName(String name);
  void setCommuterText();
  void setIntercityText();
  void setDartCommuterText();
  void setCommuterInter();
  void setRedColorText();
  void setGreenColorText();
  void setRedText();
  void setGreenText();
}

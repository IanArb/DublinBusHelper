package com.ianarbuckle.dublinbushelper.firebase.database;

/**
 * Created by Ian Arbuckle on 04/03/2017.
 *
 */

public interface DatabaseHelper {
  void sendFavouriteStopToDatabase(String time, String stopId, String name, String routes, float lat, float lon);
}

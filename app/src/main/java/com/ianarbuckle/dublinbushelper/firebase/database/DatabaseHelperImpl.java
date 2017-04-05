package com.ianarbuckle.dublinbushelper.firebase.database;

import com.google.firebase.database.FirebaseDatabase;
import com.ianarbuckle.dublinbushelper.models.Favourites;

/**
 * Created by Ian Arbuckle on 04/03/2017.
 *
 */

public class DatabaseHelperImpl implements DatabaseHelper {

  private FirebaseDatabase firebaseDatabase;

  public DatabaseHelperImpl(FirebaseDatabase firebaseDatabase) {
    this.firebaseDatabase = firebaseDatabase;
  }

  @Override
  public void sendFavouriteStopToDatabase(String time, String stopId, String name, String routes, float lat, float lon) {
    Favourites favourites = new Favourites(time, stopId, name, routes, lat, lon);
    firebaseDatabase.getReference("favourites").push().setValue(favourites);
  }
}

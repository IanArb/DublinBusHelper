package com.ianarbuckle.dublinbushelper;

import android.content.Context;

import com.ianarbuckle.dublinbushelper.firebase.authentication.FirebaseAuthHelper;
import com.ianarbuckle.dublinbushelper.firebase.database.DatabaseHelper;
import com.ianarbuckle.dublinbushelper.helper.LocationHelper;
import com.ianarbuckle.dublinbushelper.network.NetworkClient;

import dagger.Component;

/**
 * Created by Ian Arbuckle on 20/02/2017.
 *
 */
@ApplicationScope
@Component(modules = AppContextModule.class)
public interface ApplicationComponent {
  Context getContext();
  NetworkClient getNetworkClient();
  FirebaseAuthHelper getAuthHelper();
  DatabaseHelper getDatabaseHelper();
  LocationHelper getLocationHelper();
}

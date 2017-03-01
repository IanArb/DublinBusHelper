package com.ianarbuckle.dublinbushelper.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Ian Arbuckle on 20/02/2017.
 *
 */

public class NetworkClient {

  public boolean checkForConnection(Context context) {
    return isConnectedOrConnecting(context);
  }

  public static boolean isConnectedOrConnecting(Context context) {
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    return networkInfo != null && networkInfo.isConnectedOrConnecting();
  }
}

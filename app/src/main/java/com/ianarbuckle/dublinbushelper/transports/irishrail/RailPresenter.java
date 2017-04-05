package com.ianarbuckle.dublinbushelper.transports.irishrail;

import com.ianarbuckle.dublinbushelper.models.stopinfo.Result;

/**
 * Created by Ian Arbuckle on 29/03/2017.
 *
 */

public interface RailPresenter {
  void fetchStations();
  void sendToDatabase(Result result);
}

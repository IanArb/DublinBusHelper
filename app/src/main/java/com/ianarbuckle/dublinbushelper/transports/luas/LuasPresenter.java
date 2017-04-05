package com.ianarbuckle.dublinbushelper.transports.luas;

import com.ianarbuckle.dublinbushelper.models.stopinfo.Result;

/**
 * Created by Ian Arbuckle on 02/03/2017.
 *
 */

public interface LuasPresenter {
  void fetchStops();
  void sendToDatabase(Result result);
}

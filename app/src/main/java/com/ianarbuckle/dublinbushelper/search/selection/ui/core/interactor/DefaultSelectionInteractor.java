package com.ianarbuckle.dublinbushelper.search.selection.ui.core.interactor;

import com.ianarbuckle.dublinbushelper.models.stopinfo.StopInformation;
import com.ianarbuckle.dublinbushelper.network.RealTimePassengerInfoAPI;
import com.ianarbuckle.dublinbushelper.utils.Constants;

import java.util.HashMap;

import java.util.Map;

import rx.Observable;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */

public class DefaultSelectionInteractor implements SelectionInteractor {
  private final RealTimePassengerInfoAPI realTimePassengerInfoAPI;

  public DefaultSelectionInteractor(RealTimePassengerInfoAPI realTimePassengerInfoAPI) {
    this.realTimePassengerInfoAPI = realTimePassengerInfoAPI;
  }

  @Override
  public Observable<StopInformation> fetchStopInformation() {
    Map<String, String> filterValuesMap = new HashMap<>();
    filterValuesMap.put(Constants.FORMAT_KEY, Constants.FORMAT_VALUE);
    filterValuesMap.put(Constants.OPERATOR_KEY, Constants.OPERATOR_VALUE_BUS);
    return realTimePassengerInfoAPI.getStopInformation(filterValuesMap);
  }
}

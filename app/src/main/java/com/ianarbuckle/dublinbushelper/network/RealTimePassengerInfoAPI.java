package com.ianarbuckle.dublinbushelper.network;

import com.ianarbuckle.dublinbushelper.models.realtimestopinfo.RealTimeInfo;
import com.ianarbuckle.dublinbushelper.models.stopinfo.StopInformation;
import com.ianarbuckle.dublinbushelper.utils.Constants;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Ian Arbuckle on 20/02/2017.
 *
 */

public class RealTimePassengerInfoAPI {

  private final RealTimePassengerInfoService apiService;

  public RealTimePassengerInfoAPI(RealTimePassengerInfoService apiService) {
    this.apiService = apiService;
  }

  @Deprecated
  public Subscription getRealTimeInformation(final RealTimeInformationCallback callback, String stopId) {
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put(Constants.STOPID_KEY, stopId);
    dataMap.put(Constants.FORMAT_KEY, Constants.FORMAT_VALUE);
    return apiService.getRealTimeInfo(dataMap)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .onErrorResumeNext(Observable::error)
        .doOnError(throwable -> callback.onError())
        .subscribe(callback::onSuccess, throwable -> callback.onError());
  }

  @Deprecated
  public Subscription getStopInformation(final StopInformationCallback callback, Map<String, String> dataMap) {
    return apiService.getStopInfo(dataMap)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .onErrorResumeNext(Observable::error)
        .doOnError(throwable -> callback.onError())
        .subscribe(callback::onSuccess, throwable -> callback.onError());
  }

  public Observable<StopInformation> getStopInformation(Map<String, String> dataMap) {
    return apiService.getStopInfo(dataMap);
  }

  public interface RealTimeInformationCallback {
    void onSuccess(RealTimeInfo realTimeInfo);
    void onError();
  }

  public interface StopInformationCallback {
    void onSuccess(StopInformation stopInformation);
    void onError();
  }

}

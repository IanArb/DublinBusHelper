package com.ianarbuckle.dublinbushelper.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ianarbuckle.dublinbushelper.models.realtimestopinfo.RealTimeInfo;
import com.ianarbuckle.dublinbushelper.models.stopinfo.StopInformation;
import com.ianarbuckle.dublinbushelper.utils.Constants;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Ian Arbuckle on 20/02/2017.
 *
 */

public class NetworkClient {

  private final APIService apiService;

  public NetworkClient(APIService apiService) {
    this.apiService = apiService;
  }

  public Subscription getRealTimeInformation(final RealTimeInformationCallback callback, String stopId) {
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put(Constants.STOPID_KEY, stopId);
    dataMap.put(Constants.FORMAT_KEY, Constants.FORMAT_VALUE);
    return apiService.getRealTimeInfo(dataMap)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .onErrorResumeNext(new Func1<Throwable, Observable<? extends RealTimeInfo>>() {
          @Override
          public Observable<? extends RealTimeInfo> call(Throwable throwable) {
            return Observable.error(throwable);
          }
        })
        .subscribe(new Subscriber<RealTimeInfo>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable throwable) {
            callback.onError();
          }

          @Override
          public void onNext(RealTimeInfo realTimeInfo) {
            callback.onSuccess(realTimeInfo);
          }
        });
  }

  public Subscription getStopInformation(final StopInformationCallback callback, Map<String, String> dataMap) {
    return apiService.getStopInfo(dataMap)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .onErrorResumeNext(new Func1<Throwable, Observable<? extends StopInformation>>() {
          @Override
          public Observable<? extends StopInformation> call(Throwable throwable) {
            return Observable.error(throwable);
          }
        })
        .subscribe(new Subscriber<StopInformation>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable throwable) {
            callback.onError();
          }

          @Override
          public void onNext(StopInformation stopInformation) {
            callback.onSuccess(stopInformation);
          }
        });
  }

  public boolean checkForConnection(Context context) {
    return isConnectedOrConnecting(context);
  }

  private boolean isConnectedOrConnecting(Context context) {
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    return networkInfo != null && networkInfo.isConnectedOrConnecting();
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

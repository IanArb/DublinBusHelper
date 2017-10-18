package com.ianarbuckle.dublinbushelper.transports.schedules;

import com.google.android.gms.maps.GoogleMap;
import com.ianarbuckle.dublinbushelper.helper.LocationHelper;
import com.ianarbuckle.dublinbushelper.models.realtimestopinfo.RealTimeInfo;
import com.ianarbuckle.dublinbushelper.models.realtimestopinfo.Result;
import com.ianarbuckle.dublinbushelper.network.RealTimePassengerInfoAPI;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Ian Arbuckle on 31/03/2017.
 *
 */

public class SchedulePresenterImpl implements SchedulePresenter {

  @Inject
  ScheduleView view;

  @Inject
  LocationHelper locationHelper;

  @Inject
  RealTimePassengerInfoAPI realTimePassengerInfoAPI;

  private List<Result> resultList;

  private CompositeSubscription subscriptions;

  public SchedulePresenterImpl(LocationHelper locationHelper, ScheduleView view, RealTimePassengerInfoAPI realTimePassengerInfoAPI) {
    this.view = view;
    this.locationHelper = locationHelper;
    this.realTimePassengerInfoAPI = realTimePassengerInfoAPI;
    this.subscriptions = new CompositeSubscription();
    resultList = new ArrayList<>();
  }

  @Override
  public void fetchSchedules(String stopId) {
    view.showProgress();

    Subscription subscription = realTimePassengerInfoAPI.getRealTimeInformation(new RealTimePassengerInfoAPI.RealTimeInformationCallback() {
      @Override
      public void onSuccess(RealTimeInfo realTimeInfo) {
        view.hideProgress();
        resultList = realTimeInfo.getResults();
      }

      @Override
      public void onError() {
        view.hideProgress();
        view.showErrorMessage();
      }
    }, stopId);

    subscriptions.add(subscription);
  }

  @Override
  public void onBindRowViewAtPosition(int position, ScheduleRowView view) {
    Result result = resultList.get(position);

    String destination = result.getDestination();
    String direction = result.getDirection();
    String duetime = result.getDuetime();

    view.setDestination(destination);
    view.setDirection(direction);
    view.setDueTime(duetime);
  }

  @Override
  public int getResultsRowCount() {
    return resultList.size();
  }

  @Override
  public void initMap(GoogleMap googleMap) {
    locationHelper.initMap(googleMap);
  }

  @Override
  public void onRequestPermission() {
    locationHelper.onRequestPermission();
  }

  @Override
  public void onStop() {
    subscriptions.unsubscribe();
  }

}

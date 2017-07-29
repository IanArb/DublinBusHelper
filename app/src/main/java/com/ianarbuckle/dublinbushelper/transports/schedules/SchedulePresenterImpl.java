package com.ianarbuckle.dublinbushelper.transports.schedules;

import com.google.android.gms.maps.GoogleMap;
import com.ianarbuckle.dublinbushelper.helper.LocationHelper;
import com.ianarbuckle.dublinbushelper.helper.LocationHelperImpl;
import com.ianarbuckle.dublinbushelper.models.realtimestopinfo.RealTimeInfo;
import com.ianarbuckle.dublinbushelper.models.realtimestopinfo.Result;
import com.ianarbuckle.dublinbushelper.network.RTPIAPICaller;
import com.ianarbuckle.dublinbushelper.network.RTPIServiceAPI;
import com.ianarbuckle.dublinbushelper.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ian Arbuckle on 31/03/2017.
 *
 */

public class SchedulePresenterImpl implements SchedulePresenter {

  private ScheduleView view;

  RTPIServiceAPI serviceAPI;

  private List<Result> resultList;

  private LocationHelper locationHelper;

  public SchedulePresenterImpl(ScheduleView view) {
    this.view = view;
    locationHelper = new LocationHelperImpl(view.getContext());
    resultList = new ArrayList<>();
  }

  @Override
  public void fetchSchedules(String stopId) {
    view.showProgress();

    serviceAPI = RTPIAPICaller.getRTPIServiceAPI();

    Map<String, String> data = new HashMap<>();
    data.put(Constants.STOPID_KEY, stopId);
    data.put(Constants.FORMAT_KEY, Constants.FORMAT_VALUE);

    Call<RealTimeInfo> call = serviceAPI.getRealTimeInfo(data);

    getCallback(call);
  }

  private void getCallback(Call<RealTimeInfo> call) {
    call.enqueue(new Callback<RealTimeInfo>() {
      @Override
      public void onResponse(Call<RealTimeInfo> call, Response<RealTimeInfo> response) {
        view.hideProgress();
        RealTimeInfo realTimeInfo = response.body();
        resultList = realTimeInfo.getResults();
      }

      @Override
      public void onFailure(Call<RealTimeInfo> call, Throwable throwable) {
        view.hideProgress();
        view.showErrorMessage();
      }
    });
  }

  @Override
  public void onBindRowViewAtPositon(int position, ScheduleRowView view) {
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

}

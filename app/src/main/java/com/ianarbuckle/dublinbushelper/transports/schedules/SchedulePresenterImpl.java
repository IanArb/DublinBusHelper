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

  private RealTimeInfo realTimeInfo;

  private List<Result> resultList;

  ScheduleAdapter adapter;

  private LocationHelper locationHelper;

  public SchedulePresenterImpl(ScheduleView view) {
    this.view = view;
    locationHelper = new LocationHelperImpl(view.getContext());
    realTimeInfo = new RealTimeInfo();
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
        getResponseBody(response);
      }

      @Override
      public void onFailure(Call<RealTimeInfo> call, Throwable throwable) {
        view.hideProgress();
        view.showErrorMessage();
      }
    });
  }

  private void getResponseBody(Response<RealTimeInfo> response) {
    realTimeInfo = response.body();
    resultList = realTimeInfo.getResults();
    adapter = new ScheduleAdapter(view.getContext(), resultList);
    view.setAdapter(adapter);
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

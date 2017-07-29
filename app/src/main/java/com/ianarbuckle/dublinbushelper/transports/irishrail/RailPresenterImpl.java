package com.ianarbuckle.dublinbushelper.transports.irishrail;

import android.content.Intent;

import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.firebase.database.DatabaseHelper;
import com.ianarbuckle.dublinbushelper.models.stopinfo.Operator;
import com.ianarbuckle.dublinbushelper.models.stopinfo.StopInformation;
import com.ianarbuckle.dublinbushelper.models.stopinfo.Result;
import com.ianarbuckle.dublinbushelper.network.RTPIAPICaller;
import com.ianarbuckle.dublinbushelper.network.RTPIServiceAPI;
import com.ianarbuckle.dublinbushelper.transports.schedules.ScheduleActivity;
import com.ianarbuckle.dublinbushelper.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ian Arbuckle on 29/03/2017.
 *
 */

public class RailPresenterImpl implements RailPresenter {

  RTPIServiceAPI serviceAPI;

  private RailView view;

  StopInformation stopInformation;
  private List<Result> railList;

  private DatabaseHelper databaseHelper;

  public RailPresenterImpl(RailView view, DatabaseHelper databaseHelper) {
    this.view = view;
    this.databaseHelper = databaseHelper;
    stopInformation = new StopInformation();
    railList = new ArrayList<>();
  }

  @Override
  public void fetchStations() {
    view.showProgress();
    serviceAPI = RTPIAPICaller.getRTPIServiceAPI();

    Map<String, String> filterMap = new HashMap<>();
    filterMap.put(Constants.FORMAT_KEY, Constants.FORMAT_VALUE);
    filterMap.put(Constants.OPERATOR_KEY, Constants.OPERATOR_VALUE_RAIL);

    Call<StopInformation> call = serviceAPI.getStopInfo(filterMap);

    getCallback(call);

  }

  private void getCallback(Call<StopInformation> call) {
    call.enqueue(new Callback<StopInformation>() {
      @Override
      public void onResponse(Call<StopInformation> call, Response<StopInformation> response) {
        view.hideProgress();
        StopInformation stopInformation = response.body();
        railList = stopInformation.getResults();
      }

      @Override
      public void onFailure(Call<StopInformation> call, Throwable throwable) {
        view.hideProgress();
        view.showErrorMessage();
      }
    });
  }

  @Override
  public void onBindRowViewAtPositon(int position, RailCardRowView view) {
    Result result = railList.get(position);

    String fullname = result.getDisplaystopid();
    Operator operator = result.getOperators().get(0);
    String route = operator.getRoutes().toString();
    String stopid = result.getStopid();
    String lastupdated = result.getLastupdated();

    view.setName(fullname);
    view.setRoute(route);
    view.setStopId(stopid);
    view.setTime(lastupdated);
  }

  @Override
  public void onRowClickAtPosition(int position, RailCardRowView luasView) {
    Result result = railList.get(position);

    String stopId = result.getStopid();
    String displayName = result.getDisplaystopid();
    float latitude = result.getLatitude();
    float longtitude = result.getLongitude();

    Intent intent = ScheduleActivity.Companion.newIntent(view.getContext());
    intent.putExtra(Constants.STOPID_KEY, stopId);
    intent.putExtra(Constants.LAT_KEY, latitude);
    intent.putExtra(Constants.LONG_KEY, longtitude);
    intent.putExtra(Constants.DISPLAYNAME_KEY, displayName);
    view.getContext().startActivity(intent);
  }

  @Override
  public void setRouteText(int position, RailCardRowView railView) {
    Result result = railList.get(position);

    Operator operator = result.getOperators().get(0);
    String routes = operator.getRoutes().toString();

    switch (routes) {
      case Constants.COMMUTER_ID:
        railView.setCommuterText(view.getContext().getString(R.string.commuter_label));
        break;
      case Constants.INTERCITY_ID:
        railView.setIntercityText(view.getContext().getString(R.string.intercity_label));
        break;
      case Constants.COMMUTER_DART_ID:
        railView.setDartCommuterText(view.getContext().getString(R.string.dart_commuter_label));
        break;
      case Constants.DART_COMMUTER_ID:
        railView.setDartCommuterText(view.getContext().getString(R.string.dart_commuter_label));
        break;
      case Constants.COMMUTER_INTER_DART_ID:
        railView.setInterDartCommuter(view.getContext().getString(R.string.intercity_dart_commuter_label));
        break;
      case Constants.COMMUTER_INTER_ID:
        railView.setCommuterInter(view.getContext().getString(R.string.commuter_intercity_label));
        break;
      case Constants.INTER_COMMUTER_ID:
        railView.setCommuterInter(view.getContext().getString(R.string.commuter_intercity_label));
        break;
      case Constants.INTER_COMMUTER_DART_ID:
        railView.setInterDartCommuter(view.getContext().getString(R.string.intercity_dart_commuter_label));
        break;
    }
  }

  @Override
  public Result getResults(int position) {
    return railList.get(position);
  }

  @Override
  public int getResultsRowCount() {
    return railList.size();
  }

  @Override
  public List<Result> getResultsList() {
    return railList;
  }

  @Override
  public void sendToDatabase(Result result) {
    String routes = result.getOperators().get(0).getRoutes().toString();
    String lastupdated = result.getLastupdated();
    String displaystopid = result.getDisplaystopid();
    String stopid = result.getStopid();
    float lat = result.getLatitude();
    float lon = result.getLongitude();
    databaseHelper.sendFavouriteStopToDatabase(lastupdated, stopid, displaystopid, routes, lat, lon);
    view.showSuccessMessage();
  }
}

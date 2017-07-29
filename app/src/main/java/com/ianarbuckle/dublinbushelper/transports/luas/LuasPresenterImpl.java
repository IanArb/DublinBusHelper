package com.ianarbuckle.dublinbushelper.transports.luas;

import android.content.Intent;
import android.support.v4.content.ContextCompat;

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
 * Created by Ian Arbuckle on 02/03/2017.
 *
 */

public class LuasPresenterImpl implements LuasPresenter {

  private LuasView view;

  RTPIServiceAPI serviceAPI;

  StopInformation stopInformation;

  private List<Result> luasList;

  private DatabaseHelper databaseHelper;

  public LuasPresenterImpl(LuasView view, DatabaseHelper databaseHelper) {
    this.view = view;
    this.databaseHelper = databaseHelper;
    stopInformation = new StopInformation();
    luasList = new ArrayList<>();
  }

  @Override
  public void fetchStops() {
    serviceAPI = RTPIAPICaller.getRTPIServiceAPI();

    view.showProgress();

    Map<String, String> filterMap = new HashMap<>();
    filterMap.put(Constants.FORMAT_KEY, Constants.FORMAT_VALUE);
    filterMap.put(Constants.OPERATOR_KEY, Constants.OPERATOR_VALUE_LUAS);

    Call<StopInformation> call = serviceAPI.getStopInfo(filterMap);

    getRTPIResponse(call);
  }

  private void getRTPIResponse(Call<StopInformation> call) {
    call.enqueue(new Callback<StopInformation>() {
      @Override
      public void onResponse(Call<StopInformation> call, Response<StopInformation> response) {
        view.hideProgress();
        StopInformation stopInformation = response.body();
        luasList = stopInformation.getResults();
      }

      @Override
      public void onFailure(Call<StopInformation> call, Throwable throwable) {
        view.hideProgress();
        view.showErrorMessage();
      }
    });
  }

  @Override
  public void onBindRowViewAtPositon(int position, LuasCardRowView view) {
    Result result = luasList.get(position);

    String fullname = result.getFullname();
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
  public void onRowClickAtPosition(int position, LuasCardRowView luasView) {
    Result result = luasList.get(position);

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
  public void setRouteText(int position, LuasCardRowView luasView) {
    Result result = luasList.get(position);

    Operator operator = result.getOperators().get(0);
    String route = operator.getRoutes().toString();

    switch (route) {
      case Constants.RED_LINE:
        luasView.setRedText(view.getContext().getString(R.string.luas_red));
        luasView.setRedColorText(ContextCompat.getColor(view.getContext(), R.color.colorRed));
        break;
      case Constants.GREEN_LINE:
        luasView.setGreenText(view.getContext().getString(R.string.luas_green));
        luasView.setGreenColorText(ContextCompat.getColor(view.getContext(), R.color.colorGreen));
        break;
    }
  }

  @Override
  public Result getResults(int position) {
    return luasList.get(position);
  }

  @Override
  public int getResultsRowCount() {
    return luasList.size();
  }

  @Override
  public List<Result> getResultsList() {
    return luasList;
  }

  @Override
  public void sendToDatabase(Result result) {
    if(result != null) {
      String lastupdated = result.getLastupdated();
      String stopid = result.getStopid();
      String fullname = result.getFullname();
      String routes = result.getOperators().get(0).getRoutes().toString();
      float lon = result.getLongitude();
      float lat = result.getLatitude();
      databaseHelper.sendFavouriteStopToDatabase(lastupdated, stopid, fullname, routes, lat, lon);
      view.showSuccessMessage();
    }
  }
}

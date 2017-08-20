package com.ianarbuckle.dublinbushelper.transports.luas;

import android.content.Intent;
import android.support.v4.content.ContextCompat;

import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.firebase.database.DatabaseHelper;
import com.ianarbuckle.dublinbushelper.models.Favourites;
import com.ianarbuckle.dublinbushelper.models.stopinfo.Operator;
import com.ianarbuckle.dublinbushelper.models.stopinfo.StopInformation;
import com.ianarbuckle.dublinbushelper.models.stopinfo.Result;
import com.ianarbuckle.dublinbushelper.network.NetworkClient;
import com.ianarbuckle.dublinbushelper.transports.schedules.ScheduleActivity;
import com.ianarbuckle.dublinbushelper.utils.Constants;
import com.ianarbuckle.dublinbushelper.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Ian Arbuckle on 02/03/2017.
 *
 */

public class LuasPresenterImpl implements LuasPresenter {

  @Inject
  LuasView view;

  @Inject
  NetworkClient networkClient;

  @Inject
  DatabaseHelper databaseHelper;

  private CompositeSubscription subscriptions;

  private List<Result> luasList;

  public LuasPresenterImpl(LuasView view, DatabaseHelper databaseHelper, NetworkClient networkClient) {
    this.view = view;
    this.databaseHelper = databaseHelper;
    this.subscriptions = new CompositeSubscription();
    this.networkClient = networkClient;
    luasList = new ArrayList<>();
  }

  @Override
  public void fetchStops() {
    view.showProgress();

    Map<String, String> filterMap = new HashMap<>();
    filterMap.put(Constants.FORMAT_KEY, Constants.FORMAT_VALUE);
    filterMap.put(Constants.OPERATOR_KEY, Constants.OPERATOR_VALUE_LUAS);

    Subscription subscription = networkClient.getStopInformation(new NetworkClient.StopInformationCallback() {
      @Override
      public void onSuccess(StopInformation stopInformation) {
        view.hideProgress();
        luasList = stopInformation.getResults();
      }

      @Override
      public void onError() {
        view.hideProgress();
        view.showErrorMessage();
      }
    }, filterMap);
    subscriptions.add(subscription);
  }

  @Override
  public void onStop() {
    subscriptions.unsubscribe();
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

    startActivityWithIntent(stopId, displayName, latitude, longtitude);
  }

  private void startActivityWithIntent(String stopId, String displayName, float latitude, float longtitude) {
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
    String lastupdated = result.getLastupdated();
    String stopid = result.getStopid();
    String fullname = result.getFullname();
    for (Operator operator : result.getOperators()) {
      List<String> routes = operator.getRoutes();
      String route = routes.toString();
      float longitude = result.getLongitude();
      float latitude = result.getLatitude();

      if (!StringUtils.isStringEmptyorNull(lastupdated, stopid, fullname, route)) {
        Favourites favourites = new Favourites();
        favourites.setRoutes(route);
        favourites.setStopId(stopid);
        favourites.setName(fullname);
        favourites.setTime(lastupdated);
        favourites.setLongitude(longitude);
        favourites.setLatitude(latitude);
        databaseHelper.sendFavouriteStopToDatabase(favourites, Constants.FIREBASE_URL);
        view.showSuccessMessage();
      } else {
        view.showErrorMessage();
      }
    }
  }
}

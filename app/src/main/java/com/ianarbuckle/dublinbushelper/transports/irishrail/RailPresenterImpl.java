package com.ianarbuckle.dublinbushelper.transports.irishrail;

import android.content.Intent;

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
 * Created by Ian Arbuckle on 29/03/2017.
 *
 */

public class RailPresenterImpl implements RailPresenter {

  @Inject
  RailView view;

  @Inject
  NetworkClient networkClient;

  @Inject
  DatabaseHelper databaseHelper;

  private CompositeSubscription subscriptions;

  private List<Result> railList;

  public RailPresenterImpl(RailView view, DatabaseHelper databaseHelper, NetworkClient networkClient) {
    this.view = view;
    this.databaseHelper = databaseHelper;
    this.subscriptions = new CompositeSubscription();
    this.networkClient = networkClient;
    railList = new ArrayList<>();
  }

  @Override
  public void fetchStations() {
    view.showProgress();

    Map<String, String> filterMap = new HashMap<>();
    filterMap.put(Constants.FORMAT_KEY, Constants.FORMAT_VALUE);
    filterMap.put(Constants.OPERATOR_KEY, Constants.OPERATOR_VALUE_RAIL);

    Subscription subscription = networkClient.getStopInformation(new NetworkClient.StopInformationCallback() {
      @Override
      public void onSuccess(StopInformation stopInformation) {
        view.hideProgress();
        railList = stopInformation.getResults();
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
    float latitude = result.getLatitude();
    float longitude = result.getLongitude();

    if (!StringUtils.isStringEmptyorNull(routes, lastupdated, displaystopid, stopid)) {
      Favourites favourites = new Favourites();
      favourites.setRoutes(routes);
      favourites.setStopId(stopid);
      favourites.setName(displaystopid);
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

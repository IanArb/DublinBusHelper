package com.ianarbuckle.dublinbushelper.transports.dublinbus;

import android.support.v4.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;
import com.ianarbuckle.dublinbushelper.firebase.database.DatabaseHelper;
import com.ianarbuckle.dublinbushelper.helper.LocationHelper;
import com.ianarbuckle.dublinbushelper.models.Favourites;
import com.ianarbuckle.dublinbushelper.models.MarkerItemModel;
import com.ianarbuckle.dublinbushelper.models.stopinfo.StopInformation;
import com.ianarbuckle.dublinbushelper.models.stopinfo.Operator;
import com.ianarbuckle.dublinbushelper.models.stopinfo.Result;
import com.ianarbuckle.dublinbushelper.network.NetworkClient;
import com.ianarbuckle.dublinbushelper.utils.Constants;
import com.ianarbuckle.dublinbushelper.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Ian Arbuckle on 19/02/2017.
 *
 */

public class DublinBusPresenterImpl implements DublinBusPresenter {

  @Inject
  DublinBusView view;

  @Inject
  DialogCallback dialogCallback;

  @Inject
  StopInformation stopInformation;

  @Inject
  List<Result> dublinBusList;

  @Inject
  LocationHelper locationHelper;

  @Inject
  DatabaseHelper databaseHelper;

  @Inject
  NetworkClient networkClient;

  private CompositeSubscription subscriptions;

  private ClusterManager<MarkerItemModel> clusterManager;

  public DublinBusPresenterImpl(NetworkClient networkClient, LocationHelper locationHelper, DublinBusView view) {
    this.locationHelper = locationHelper;
    this.view = view;
    this.networkClient = networkClient;
    this.subscriptions = new CompositeSubscription();
  }

  public DublinBusPresenterImpl(DatabaseHelper databaseHelper, DialogCallback dialogCallback) {
    this.dialogCallback = dialogCallback;
    this.databaseHelper = databaseHelper;
  }

  @Override
  public boolean checkLocationPermission(Fragment fragment) {
    return locationHelper.checkLocationPermission(fragment);
  }

  @Override
  public void initMap(GoogleMap googleMap) {
    locationHelper.initMap(googleMap);
    setupCluster(googleMap);
  }

  private void setupCluster(GoogleMap googleMap) {
    clusterManager = new ClusterManager<>(view.getContext(), googleMap);
    final BusMarkerRenderer clusterRenderer = new BusMarkerRenderer(view.getContext(), googleMap, clusterManager);
    googleMap.setOnMarkerClickListener(clusterManager);
    clusterManager.setRenderer(clusterRenderer);
    googleMap.setOnCameraChangeListener(clusterManager);
  }

  @Override
  public void fetchStops() {
    view.showProgress();

    Map<String, String> filterMap = new HashMap<>();
    filterMap.put(Constants.FORMAT_KEY, Constants.FORMAT_VALUE);
    filterMap.put(Constants.OPERATOR_KEY, Constants.OPERATOR_VALUE_BUS);

    Subscription subscription = networkClient.getStopInformation(new NetworkClient.StopInformationCallback() {
      @Override
      public void onSuccess(StopInformation stopInformation) {
        view.hideProgress();
        dublinBusList = stopInformation.getResults();
        drawMarkerItems();
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

  private void drawMarkerItems() {
    for (final Result result : dublinBusList) {
      MarkerItemModel items = getMarkerItems(result);
      clusterManager.addItem(items);
      setOnClickListener();
    }
  }

  private MarkerItemModel getMarkerItems(Result result) {
    float latitude = result.getLatitude();
    float longitude = result.getLongitude();
    LatLng latLng = new LatLng(latitude, longitude);
    String displaystopid = result.getStopid();
    String shortname = result.getShortname();
    boolean isIrish = Locale.getDefault().getLanguage().equals(Constants.IRISH_INTERNATIONAL_VALUE);
    String shortnamelocalized = null;
    if (isIrish) {
      shortnamelocalized = result.getShortnamelocalized();
      view.setLocalVisibility();
    }
    String lastupdated = result.getLastupdated();
    String routes = null;
    for (Operator operator : result.getOperators()) {
      routes = operator.getRoutes().toString();
    }

    return new MarkerItemModel(latLng, displaystopid, shortname, shortnamelocalized, lastupdated, routes);
  }

  private void setOnClickListener() {
    clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MarkerItemModel>() {
      @Override
      public boolean onClusterItemClick(MarkerItemModel markerItemModel) {
        String displayStopId = markerItemModel.getDisplayStopId();
        String shortName = markerItemModel.getShortName();
        String shortNameLocalised = markerItemModel.getShortNameLocalised();
        String lastUpdated = markerItemModel.getLastUpdated();
        String routes = markerItemModel.getRoutes().trim();
        LatLng latLng = markerItemModel.getPosition();
        float latitude = ((float) latLng.latitude);
        float longitude = ((float) latLng.longitude);
        view.showPopupFragment(displayStopId, shortName, shortNameLocalised, lastUpdated, routes, latitude, longitude);
        return false;
      }
    });
  }

  @Override
  public void sendToDatabase(String shortName, float latitude, float longtitude, String lastUpdated, String displayStopId, String routes) {
    Favourites favourites = new Favourites();
    favourites.setName(shortName);
    favourites.setLatitude(latitude);
    favourites.setLongitude(longtitude);
    favourites.setTime(lastUpdated);
    favourites.setStopId(displayStopId);

    if (!StringUtils.isStringEmptyorNull(lastUpdated, displayStopId, shortName, routes)) {
      databaseHelper.sendFavouriteStopToDatabase(favourites, Constants.FIREBASE_URL);
      dialogCallback.onSuccess();
    } else {
      dialogCallback.onFailure();
    }
  }

  @Override
  public void onRequestPermission() {
    locationHelper.onRequestPermission();
  }

  public interface DialogCallback {
    void onSuccess();

    void onFailure();
  }

}

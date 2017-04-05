package com.ianarbuckle.dublinbushelper.transports.dublinbus;

import android.support.v4.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;
import com.ianarbuckle.dublinbushelper.firebase.database.DatabaseHelper;
import com.ianarbuckle.dublinbushelper.helper.LocationHelper;
import com.ianarbuckle.dublinbushelper.helper.LocationHelperImpl;
import com.ianarbuckle.dublinbushelper.models.MarkerItemModel;
import com.ianarbuckle.dublinbushelper.models.stopinfo.StopInformation;
import com.ianarbuckle.dublinbushelper.models.stopinfo.Operator;
import com.ianarbuckle.dublinbushelper.models.stopinfo.Result;
import com.ianarbuckle.dublinbushelper.network.RTPIAPICaller;
import com.ianarbuckle.dublinbushelper.network.RTPIServiceAPI;
import com.ianarbuckle.dublinbushelper.utils.Constants;
import com.ianarbuckle.dublinbushelper.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ian Arbuckle on 19/02/2017.
 *
 */

public class DublinBusPresenterImpl implements DublinBusPresenter {

  private DublinBusView view;
  private DublinBusFragmentView fragmentView;

  private StopInformation stopInformation;
  private List<Result> dublinBusList;

  private LocationHelper locationHelper;
  private ClusterManager<MarkerItemModel> clusterManager;

  private DatabaseHelper databaseHelper;

  RTPIServiceAPI serviceAPI;

  public DublinBusPresenterImpl(DatabaseHelper databaseHelper) {
    this.databaseHelper = databaseHelper;
    stopInformation = new StopInformation();
    dublinBusList = new ArrayList<>();
  }

  @Override
  public void setView(DublinBusView view) {
    this.view = view;
    locationHelper = new LocationHelperImpl(view.getContext());
  }

  @Override
  public void setFragmentView(DublinBusFragmentView view) {
    this.fragmentView = view;
  }

  @Override
  public boolean checkLocationPermission(Fragment fragment) {
    return locationHelper.checkLocationPermission(fragment);
  }

  @Override
  public void fetchStops() {
    serviceAPI = RTPIAPICaller.getRTPIServiceAPI();

    view.showProgress();

    Map<String, String> filterMap = new HashMap<>();
    filterMap.put(Constants.FORMAT_KEY, Constants.FORMAT_VALUE);
    filterMap.put(Constants.OPERATOR_KEY, Constants.OPERATOR_VALUE_BUS);

    Call<StopInformation> call = serviceAPI.getStopInfo(filterMap);

    getCallback(call);
  }

  private void getCallback(Call<StopInformation> call) {
    call.enqueue(new Callback<StopInformation>() {
      @Override
      public void onResponse(Call<StopInformation> call, Response<StopInformation> response) {
        view.hideProgress();
        getResponseBody(response);
      }

      @Override
      public void onFailure(Call<StopInformation> call, Throwable throwable) {
        view.hideProgress();
        view.showErrorMessage();
      }
    });

  }

  private void getResponseBody(final Response<StopInformation> response) {
    stopInformation = response.body();
    dublinBusList = stopInformation.getResults();
    for(final Result results : dublinBusList) {
      MarkerItemModel items = getMarkerItems(results);
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
    if(isIrish) {
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
        String routes = markerItemModel.getRoutes();
        LatLng latLng = markerItemModel.getPosition();
        float latitude = ((float) latLng.latitude);
        float longitude = ((float) latLng.longitude);
        view.showPopupFragment(displayStopId, shortName, shortNameLocalised, lastUpdated, routes, latitude, longitude);
        return false;
      }
    });
  }

  @Override
  public void initMap(GoogleMap googleMap) {
    locationHelper.initMap(googleMap);
    clusterManager = new ClusterManager<>(view.getContext(), googleMap);
    final BusMarkerRenderer clusterRenderer = new BusMarkerRenderer(view.getContext(), googleMap, clusterManager);
    googleMap.setOnMarkerClickListener(clusterManager);
    clusterManager.setRenderer(clusterRenderer);
    googleMap.setOnCameraChangeListener(clusterManager);
  }

  @Override
  public void sendToDatabase(String lastUpdate, String stopid, String name, String routes, float latitude, float longtitude) {
    if(!StringUtils.isStringEmptyorNull(lastUpdate, stopid, name, routes)) {
      databaseHelper.sendFavouriteStopToDatabase(lastUpdate, stopid, name, routes, latitude, longtitude);
      fragmentView.setSuccessMessage();
    } else {
      fragmentView.setErrorMessage();
    }

  }

  @Override
  public void onRequestPermission() {
    locationHelper.onRequestPermission();
  }

}
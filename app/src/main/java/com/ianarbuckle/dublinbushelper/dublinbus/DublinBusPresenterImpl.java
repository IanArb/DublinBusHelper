package com.ianarbuckle.dublinbushelper.dublinbus;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;
import com.ianarbuckle.dublinbushelper.helper.LocationHelper;
import com.ianarbuckle.dublinbushelper.helper.LocationHelperImpl;
import com.ianarbuckle.dublinbushelper.models.MarkerItemModel;
import com.ianarbuckle.dublinbushelper.models.busstopinfo.BusStopInformation;
import com.ianarbuckle.dublinbushelper.models.busstopinfo.Operator;
import com.ianarbuckle.dublinbushelper.models.busstopinfo.Result;
import com.ianarbuckle.dublinbushelper.network.RTPIAPICaller;
import com.ianarbuckle.dublinbushelper.network.RTPIServiceAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ian Arbuckle on 19/02/2017.
 *
 */

public class DublinBusPresenterImpl implements DublinBusPresenter {

  private DublinBusView view;

  RTPIServiceAPI serviceAPI;

  private BusStopInformation busStopInformation;

  private List<Result> dublinBusList;

  private LocationHelper locationHelper;

  private ClusterManager<MarkerItemModel> clusterManager;

  public DublinBusPresenterImpl(DublinBusView view) {
    this.view = view;
    busStopInformation = new BusStopInformation();
    dublinBusList = new ArrayList<>();
    locationHelper = new LocationHelperImpl(view.getContext());
  }

  @Override
  public void fetchStops() {
    serviceAPI = RTPIAPICaller.getRTPIServiceAPI();

    view.showProgress();

    Call<BusStopInformation> call = serviceAPI.getBusStopInfo();

    getRTPIResponse(call);
  }

  private void getRTPIResponse(Call<BusStopInformation> call) {
    call.enqueue(new Callback<BusStopInformation>() {
      @Override
      public void onResponse(Call<BusStopInformation> call, Response<BusStopInformation> response) {
        view.hideProgress();
        getResponseBody(response);
      }

      @Override
      public void onFailure(Call<BusStopInformation> call, Throwable throwable) {
        view.hideProgress();
        view.showErrorMessage();
      }
    });

  }

  private void getResponseBody(final Response<BusStopInformation> response) {
    busStopInformation = response.body();
    dublinBusList = busStopInformation.getResults();
    //Temporary toast message for testing purposes
    Toast.makeText(view.getContext(), "Latitude: " + dublinBusList.get(0).getLatitude() + " Longitude: " + dublinBusList.get(0).getLongitude(), Toast.LENGTH_SHORT).show();
    for(final Result results : dublinBusList) {
      for(final Operator operators : results.getOperators()) {
        boolean isDublinBus = operators.getName().equals("bac");
        getType(results, isDublinBus);
      }
    }
  }

  private void getType(Result results, boolean isOperator) {
    if(isOperator) {
      MarkerItemModel items = getMarkerItems(results);
      clusterManager.getClusterMarkerCollection().getMarkers();
      clusterManager.addItem(items);
      setOnClickListener();
    }
  }

  @Override
  public MarkerItemModel getMarkerItems(Result result) {
    double latitude = result.getLatitude();
    double longitude = result.getLongitude();
    LatLng latLng = new LatLng(latitude, longitude);
    String displaystopid = result.getDisplaystopid();
    String shortname = result.getShortname();
    boolean isIrish = Locale.getDefault().getLanguage().equals("ga_IE");
    String shortnamelocalized = null;
    if(isIrish) {
      shortnamelocalized = result.getShortnamelocalized();
    }
    String lastupdated = result.getLastupdated();
    String list = null;
    for (Operator operator : result.getOperators()) {
      list = operator.getRoutes().toString();
    }
    return new MarkerItemModel(latLng, displaystopid, shortname, shortnamelocalized, lastupdated, list);
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
        view.showPopupFragment(displayStopId, shortName, shortNameLocalised, lastUpdated, routes);
        return false;
      }
    });
  }

  @Override
  public boolean checkLocationPermission(Fragment fragment) {
    return locationHelper.checkLocationPermission(fragment);
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
  public void onRequestPermission() {
    locationHelper.onRequestPermission();
  }
}

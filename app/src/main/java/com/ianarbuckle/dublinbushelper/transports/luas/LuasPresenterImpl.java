package com.ianarbuckle.dublinbushelper.transports.luas;

import com.ianarbuckle.dublinbushelper.firebase.database.DatabaseHelper;
import com.ianarbuckle.dublinbushelper.models.stopinfo.StopInformation;
import com.ianarbuckle.dublinbushelper.models.stopinfo.Result;
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
 * Created by Ian Arbuckle on 02/03/2017.
 *
 */

public class LuasPresenterImpl implements LuasPresenter {

  private LuasView view;

  RTPIServiceAPI serviceAPI;

  private StopInformation stopInformation;

  private List<Result> luasList;

  LuasAdapter luasAdapter;

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
    luasList = stopInformation.getResults();
    luasAdapter = new LuasAdapter(luasList, view.getContext());
    view.setAdapter(luasAdapter);
    view.setFilter();
    luasAdapter.getFilter().filter(view.setFilter());
    luasAdapter.updateList(luasList);
  }

  @Override
  public void sendToDatabase(Result result) {
    assert result != null;
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

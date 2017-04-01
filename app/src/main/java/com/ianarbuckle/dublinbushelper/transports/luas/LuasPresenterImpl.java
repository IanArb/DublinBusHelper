package com.ianarbuckle.dublinbushelper.transports.luas;

import com.ianarbuckle.dublinbushelper.models.stopinfo.StopInformation;
import com.ianarbuckle.dublinbushelper.models.stopinfo.Result;
import com.ianarbuckle.dublinbushelper.network.RTPIAPICaller;
import com.ianarbuckle.dublinbushelper.network.RTPIServiceAPI;

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

  LuasView view;

  RTPIServiceAPI serviceAPI;

  private StopInformation stopInformation;

  private List<Result> luasList;

  LuasAdapter luasAdapter;

  public LuasPresenterImpl(LuasView view) {
    this.view = view;
    stopInformation = new StopInformation();
    luasList = new ArrayList<>();
  }

  @Override
  public void fetchStops() {
    serviceAPI = RTPIAPICaller.getRTPIServiceAPI();

    view.showProgress();

    Map<String, String> filterMap = new HashMap<>();
    filterMap.put("format", "json");
    filterMap.put("operator", "LUAS");

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
  }

}

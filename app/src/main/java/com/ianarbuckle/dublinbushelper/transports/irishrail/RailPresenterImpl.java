package com.ianarbuckle.dublinbushelper.transports.irishrail;

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
 * Created by Ian Arbuckle on 29/03/2017.
 *
 */

public class RailPresenterImpl implements RailPresenter {

  RTPIServiceAPI serviceAPI;

  private RailView view;
  RailAdapter adapter;

  private StopInformation stopInformation;
  private List<Result> resultList;

  public RailPresenterImpl(RailView view) {
    this.view = view;
    stopInformation = new StopInformation();
    resultList = new ArrayList<>();
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
        getResponseBody(response);
      }

      @Override
      public void onFailure(Call<StopInformation> call, Throwable throwable) {
        view.hideProgress();
        view.showErrorMessage();
      }
    });
  }

  private void getResponseBody(Response<StopInformation> response) {
    stopInformation = response.body();
    resultList = stopInformation.getResults();
    adapter = new RailAdapter(view.getContext(), resultList);
    view.setAdapter(adapter);
  }

}

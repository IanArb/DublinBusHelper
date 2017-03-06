package com.ianarbuckle.dublinbushelper.luas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ianarbuckle.dublinbushelper.BaseFragment;
import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.models.busstopinfo.BusStopInformation;
import com.ianarbuckle.dublinbushelper.models.busstopinfo.Result;
import com.ianarbuckle.dublinbushelper.network.RTPIAPICaller;
import com.ianarbuckle.dublinbushelper.network.RTPIServiceAPI;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ian Arbuckle on 02/03/2017.
 *
 */

public class LuasFragment extends BaseFragment implements LuasView {

  RTPIServiceAPI serviceAPI;

  BusStopInformation busStopInformation = new BusStopInformation();
  List<Result> luasList = new ArrayList<>();

  RecyclerView.Adapter adapter;

  LinearLayoutManager linearLayoutManager;

  @BindView(R.id.rv)
  RecyclerView recyclerView;

  public static Fragment newInstance() {
    return new LuasFragment();
  }

  @Override
  protected void initPresenter() {

  }

  @Override
  public void onStart() {
    super.onStart();
    fetchStops();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_luas, container, false);
    ButterKnife.bind(this, view);
    recyclerView.setHasFixedSize(true);
    linearLayoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(linearLayoutManager);
    return view;
  }

  private void fetchStops() {
    serviceAPI = RTPIAPICaller.getRTPIServiceAPI();

    Call<BusStopInformation> call = serviceAPI.getBusStopInfo();

    showProgressDialog();

    getRTPIResponse(call);
  }

  private void getRTPIResponse(Call<BusStopInformation> call) {
    call.enqueue(new Callback<BusStopInformation>() {
      @Override
      public void onResponse(Call<BusStopInformation> call, Response<BusStopInformation> response) {
        hideProgressDialog();
        busStopInformation = response.body();
        luasList = busStopInformation.getResults();
        adapter = new LuasAdapter(luasList);
        recyclerView.setAdapter(adapter);
      }

      @Override
      public void onFailure(Call<BusStopInformation> call, Throwable throwable) {
        hideProgressDialog();
      }
    });
  }

}

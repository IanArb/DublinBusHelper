package com.ianarbuckle.dublinbushelper.search.searchlist.route.ui.core.presenter;

import android.util.Log;

import com.ianarbuckle.dublinbushelper.models.routeinformation.Result;
import com.ianarbuckle.dublinbushelper.search.searchlist.route.RouteSearchListView;
import com.ianarbuckle.dublinbushelper.search.searchlist.route.ui.core.interactor.RouteSearchListInteractor;
import com.ianarbuckle.dublinbushelper.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Ian Arbuckle on 30/10/2017.
 *
 */

public class DefaultRouteSearchListPresenter implements RouteSearchListPresenter {
  private final RouteSearchListView view;
  private final RouteSearchListInteractor interactor;
  private final CompositeSubscription subscriptions;
  private List<Result> results;

  public DefaultRouteSearchListPresenter(RouteSearchListView view, RouteSearchListInteractor interactor) {
    this.view = view;
    this.interactor = interactor;
    subscriptions = new CompositeSubscription();
    results = new ArrayList<>();
  }

  @Override
  public void onCreate() {
    subscriptions.add(fetchRouteListInformation());
  }

  private Subscription fetchRouteListInformation() {
    return interactor.fetchRouteListInformation()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .onErrorResumeNext(Observable::error)
        .doOnError(throwable -> view.showError())
        .subscribe(routeListInformation -> {
          String route = routeListInformation.getRoute();
          String operator = routeListInformation.getOperator();
          Map<String, String> hashMap = new HashMap<>();
          hashMap.put(Constants.ROUTE_KEY, "123");
          hashMap.put(Constants.OPERATOR_KEY, Constants.OPERATOR_VALUE_BUS);
          fetchRouteInformation(hashMap);
        }, throwable -> view.showError());
  }

  private void fetchRouteInformation(Map<String, String> hashMap) {
    interactor.fetchRouteInformation(hashMap)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .onErrorResumeNext(Observable::error)
        .doOnError(throwable -> view.showError())
        .subscribe(routeInformation -> {
          results = routeInformation.getResults();
          view.setResults(results);
        }, throwable -> Log.e("error", throwable.toString()));
  }

  @Override
  public void onDestroy() {
    subscriptions.clear();
  }
}

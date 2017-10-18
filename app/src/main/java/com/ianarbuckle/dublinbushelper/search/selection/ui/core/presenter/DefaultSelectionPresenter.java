package com.ianarbuckle.dublinbushelper.search.selection.ui.core.presenter;

import com.ianarbuckle.dublinbushelper.models.stopinfo.Result;
import com.ianarbuckle.dublinbushelper.search.selection.ui.SelectionView;
import com.ianarbuckle.dublinbushelper.search.selection.ui.core.interactor.SelectionInteractor;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */

public class DefaultSelectionPresenter implements SelectionPresenter {
  private final SelectionView view;
  private final SelectionInteractor interactor;
  private final CompositeSubscription subscriptions;
  private List<Result> results;

  public DefaultSelectionPresenter(SelectionView view, SelectionInteractor interactor) {
    this.view = view;
    this.interactor = interactor;
    subscriptions = new CompositeSubscription();
    results = new ArrayList<>();
  }

  public void onCreate() {
    subscriptions.add(fetchStops());
  }

  private Subscription fetchStops() {
    view.showProgress();
    return interactor.fetchStopInformation()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .onErrorResumeNext(Observable::error)
        .doOnError(throwable -> view.showError())
        .subscribe(stopInformation -> {
          results = stopInformation.getResults();
          view.setResults(results);
          view.hideProgress();
        }, throwable -> view.showError());
  }

  public void onDestroy() {
    subscriptions.clear();
  }

}

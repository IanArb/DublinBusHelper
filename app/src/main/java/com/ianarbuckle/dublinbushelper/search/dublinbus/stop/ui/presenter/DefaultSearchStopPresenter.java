package com.ianarbuckle.dublinbushelper.search.dublinbus.stop.ui.presenter;

import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.ui.SearchStopView;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.wireframe.SearchStopWireframe;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */

public class DefaultSearchStopPresenter implements SearchStopPresenter {
  private final SearchStopView view;
  private final CompositeSubscription subscriptions;
  private final SearchStopWireframe wireframe;

  public DefaultSearchStopPresenter(SearchStopView view, SearchStopWireframe wireframe) {
    this.view = view;
    this.wireframe = wireframe;
    subscriptions = new CompositeSubscription();
  }

  public void onCreate() {
    subscriptions.addAll(onCardClick(), onContinueClick());
  }

  private Subscription onCardClick() {
    return view.getCardClicks().subscribe(view -> wireframe.onClickCard());
  }

  private Subscription onContinueClick() {
    return view.getContinueClicks().subscribe(view -> wireframe.onClickContinue());
  }

  public void onDestroy() {
    subscriptions.clear();
  }

}

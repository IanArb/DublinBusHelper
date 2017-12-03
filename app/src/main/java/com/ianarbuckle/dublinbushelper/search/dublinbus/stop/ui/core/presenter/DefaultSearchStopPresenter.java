package com.ianarbuckle.dublinbushelper.search.dublinbus.stop.ui.core.presenter;

import android.util.Log;

import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.ui.core.view.SearchStopView;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.wireframe.SearchStopWireframe;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */

public class DefaultSearchStopPresenter implements SearchStopPresenter {
  private final SearchStopWireframe wireframe;
  private final SearchStopView view;
  private final CompositeSubscription subscriptions;

  public DefaultSearchStopPresenter(SearchStopView view, SearchStopWireframe wireframe) {
    this.view = view;
    this.wireframe = wireframe;
    subscriptions = new CompositeSubscription();
  }

  @Override
  public void onStart() {
    Log.i("Presenter", "Called stopPresenter");
    subscriptions.add(onCardClick());
    subscriptions.add(onContinueClick());
  }

  private Subscription onCardClick() {
    return view.getCardClicks()
        .subscribe(view -> wireframe.onClickCard());
  }

  private Subscription onContinueClick() {
    return view.getContinueClicks()
        .subscribe(view -> wireframe.onStopClickContinue());
  }

  @Override
  public void onDestroy() {
    subscriptions.clear();
  }
}

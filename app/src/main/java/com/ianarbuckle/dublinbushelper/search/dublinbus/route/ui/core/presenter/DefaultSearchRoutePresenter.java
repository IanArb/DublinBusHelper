package com.ianarbuckle.dublinbushelper.search.dublinbus.route.ui.core.presenter;

import android.util.Log;
import android.widget.Toast;

import com.ianarbuckle.dublinbushelper.search.dublinbus.route.ui.core.view.SearchRouteView;
import com.ianarbuckle.dublinbushelper.search.dublinbus.route.ui.core.model.ValidationResult;
import com.ianarbuckle.dublinbushelper.search.dublinbus.route.wireframe.SearchRouteWireframe;
import com.ianarbuckle.dublinbushelper.utils.StringUtils;

import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Ian Arbuckle on 19/10/2017.
 *
 */

public class DefaultSearchRoutePresenter implements SearchRoutePresenter {
  private final SearchRouteWireframe wireframe;
  private final SearchRouteView view;
  private final CompositeSubscription subscriptions;

  public DefaultSearchRoutePresenter(SearchRouteView view, SearchRouteWireframe wireframe) {
    this.view = view;
    this.wireframe = wireframe;
    subscriptions = new CompositeSubscription();
  }

  @Override
  public void onCreate() {
    subscriptions.add(subscribeOnFocusChangeField());
    subscriptions.add(subscribeOnRouteIdField());
    subscriptions.add(subscribeOnContinueClicks());
  }

  private Subscription subscribeOnFocusChangeField() {
    return view.observeChangeFocusField()
        .skip(1)
        .subscribe(hasFocus -> {
          if(!hasFocus) {
            ValidationResult result = validateRouteId(view.getFieldInput());
            view.showError(result.getReason());
          }
        });
  }

  private Subscription subscribeOnRouteIdField() {
    Observable<Boolean> routeObservable = view.getObservableRouteId()
        .debounce(800, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .map(routeId -> {
          ValidationResult result = validateRouteId(routeId);
          view.showError(result.getReason());
          return result.isValid();
        });
    return routeObservable
        .subscribe(valid -> view.enableContinueButton());
  }

  private void validateRouteId() {
    String fieldInput = view.getFieldInput();
    ValidationResult<String> routeIdValid = StringUtils.isRouteIdValid(fieldInput);

    if(routeIdValid.isValid()) {
      view.showError(routeIdValid.getReason());
    } else {
      view.enableContinueButton();
    }
  }

  private ValidationResult<String> validateRouteId(@Nullable String routeId) {
    return StringUtils.isRouteIdValid(routeId);
  }

  private Subscription subscribeOnContinueClicks() {
    return view.getContinueClicksObservable()
        .subscribe(view -> wireframe.onContinueClick());
  }

  @Override
  public void onDestroy() {
    subscriptions.clear();
  }
}

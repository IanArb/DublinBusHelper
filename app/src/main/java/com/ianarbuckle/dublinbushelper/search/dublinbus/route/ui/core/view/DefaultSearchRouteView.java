package com.ianarbuckle.dublinbushelper.search.dublinbus.route.ui.core.view;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.utils.RxHelper;
import com.jakewharton.rxbinding.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * Created by Ian Arbuckle on 04/11/2017.
 *
 */
public class DefaultSearchRouteView extends LinearLayout implements SearchRouteView {

  @BindView(R.id.tilRouteId)
  TextInputLayout tilRouteId;

  @BindView(R.id.etRouteId)
  EditText etRouteId;

  @BindView(R.id.btnContinueRoute)
  Button btnContinue;

  public DefaultSearchRouteView(Context context) {
    super(context);
    inflate(getContext(), R.layout.layout_search_route, this);
    ButterKnife.bind(this);
  }

  @Override
  public View getView() {
    return this;
  }

  @Override
  public Observable<Boolean> observeChangeFocusField() {
    return RxView.focusChanges(etRouteId);
  }

  @Override
  public Observable<String> getObservableRouteId() {
    return RxHelper.getTextWatcherObservable(etRouteId);
  }

  @Override
  public Observable<Void> getContinueClicksObservable() {
    return RxView.clicks(btnContinue);
  }

  @Override
  public void showError(String reason) {
    tilRouteId.setErrorEnabled(true);
    tilRouteId.setError(reason);
  }

  @Override
  public void enableContinueButton() {
    btnContinue.setAlpha(1f);
    btnContinue.setEnabled(true);
  }

  @Override
  public String getFieldInput() {
    return etRouteId.getText().toString().trim();
  }
}

package com.ianarbuckle.dublinbushelper.search.dublinbus.stop.ui.core.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.utils.Constants;
import com.ianarbuckle.dublinbushelper.utils.StringUtils;
import com.jakewharton.rxbinding.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */

public class DefaultSearchStopView extends FrameLayout implements SearchStopView {

  @BindView(R.id.tvLabel)
  TextView tvLabel;

  @BindView(R.id.tvValue)
  TextView tvValue;

  @BindView(R.id.btnContinue)
  Button btnContinue;

  @BindView(R.id.cardView)
  CardView cardView;

  public DefaultSearchStopView(Context context) {
    super(context);
    inflate(getContext(), R.layout.layout_search, this);
    ButterKnife.bind(this);
    tvLabel.setText(R.string.search_stops_card_placeholder);
  }

  @Override
  public View getView() {
    return this;
  }

  @Override
  public Observable<Void> getCardClicks() {
    return RxView.clicks(cardView);
  }

  @Override
  public Observable<Void> getContinueClicks() {
    return RxView.clicks(btnContinue);
  }

  @Override
  public void setCardValues(Intent intent) {
    if(intent != null) {
      String stopId = intent.getExtras().getString(Constants.STOPID_KEY);
      String stopName = intent.getExtras().getString(Constants.NAME_KEY);
      if (StringUtils.isStringEmptyorNull(stopId, stopName)) {
        tvLabel.setText(R.string.search_stops_card_placeholder);
      } else {
        tvValue.setVisibility(View.VISIBLE);
        btnContinue.setEnabled(true);
        btnContinue.setAlpha(1f);
        tvLabel.setText(stopId);
        tvValue.setText(stopName);
      }
    }
  }

}

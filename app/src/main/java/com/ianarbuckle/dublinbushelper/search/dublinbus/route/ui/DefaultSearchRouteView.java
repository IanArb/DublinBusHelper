package com.ianarbuckle.dublinbushelper.search.dublinbus.route.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ianarbuckle.dublinbushelper.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */

public class DefaultSearchRouteView extends FrameLayout implements SearchRouteView {

  @BindView(R.id.tvLabel)
  TextView tvLabel;

  public DefaultSearchRouteView(@NonNull Context context) {
    super(context);
    inflate(context, R.layout.layout_search, this);
    ButterKnife.bind(this);
    tvLabel.setText("Tap to find routes");
  }

  @Override
  public View getView() {
    return this;
  }
}

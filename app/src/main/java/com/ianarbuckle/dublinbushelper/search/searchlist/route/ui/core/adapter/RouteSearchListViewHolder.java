package com.ianarbuckle.dublinbushelper.search.searchlist.route.ui.core.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ianarbuckle.dublinbushelper.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 02/11/2017.
 *
 */

public class RouteSearchListViewHolder extends RecyclerView.ViewHolder implements RouteSearchListViewRow {

  @BindView(R.id.tvStopNumber)
  TextView tvStopNumber;

  @BindView(R.id.tvDestination)
  TextView tvDestination;

  @BindView(R.id.tvOrigin)
  TextView tvOrigin;

  public RouteSearchListViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  @Override
  public void setStopNumber(String stopNumber) {
    tvStopNumber.setText(stopNumber);
  }

  @Override
  public void setOrigin(String origin) {
    tvOrigin.setText(origin);
  }

  @Override
  public void setDestination(String destination) {
    tvDestination.setText(destination);
  }
}

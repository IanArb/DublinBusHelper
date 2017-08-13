package com.ianarbuckle.dublinbushelper.favourites;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ianarbuckle.dublinbushelper.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 03/04/2017.
 *
 */

public class FavouritesViewHolder extends RecyclerView.ViewHolder implements FavouritesCardRowView {

  @BindView(R.id.tvTime)
  TextView tvTime;

  @BindView(R.id.tvStopid)
  TextView tvStopId;

  @BindView(R.id.tvName)
  TextView tvName;

  @BindView(R.id.tvRoute)
  TextView tvRoute;

  @BindView(R.id.btnSchedule)
  Button btnSchedule;

  @BindView(R.id.ibRemove)
  ImageButton ibRemove;

  public FavouritesViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  @Override
  public void setTime(String time) {
    tvTime.setText(time);
  }

  @Override
  public void setStopId(String stopId) {
    tvStopId.setText(stopId);
  }

  @Override
  public void setRoute(String route) {
    tvRoute.setText(route);
  }

  @Override
  public void setName(String name) {
    tvName.setText(name);
  }

  @Override
  public void setCommuterText() {
    tvRoute.setText(itemView.getContext().getString(R.string.commuter_label));
  }

  @Override
  public void setIntercityText() {
    tvRoute.setText(itemView.getContext().getString(R.string.intercity_label));
  }

  @Override
  public void setDartCommuterText() {
    tvRoute.setText(itemView.getContext().getString(R.string.dart_commuter_label));
  }

  @Override
  public void setCommuterInter() {
    tvRoute.setText(itemView.getContext().getString(R.string.intercity_dart_commuter_label));
  }

  @Override
  public void setRedColorText() {
    tvRoute.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorRed));
  }

  @Override
  public void setGreenColorText() {
    tvRoute.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorGreen));
  }

  @Override
  public void setRedText() {
    tvRoute.setText(itemView.getContext().getString(R.string.luas_red));
  }

  @Override
  public void setGreenText() {
    tvRoute.setText(itemView.getContext().getString(R.string.luas_green));
  }
}

package com.ianarbuckle.dublinbushelper.transports.luas;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ianarbuckle.dublinbushelper.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 28/07/2017.
 *
 */

public class LuasCardViewHolder extends RecyclerView.ViewHolder implements LuasCardRowView {

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

  @BindView(R.id.ibFavourite)
  ImageButton ibFavourite;

  public LuasCardViewHolder(View itemView) {
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
  public void setName(String name) {
    tvName.setText(name);
  }

  @Override
  public void setRoute(String route) {
    tvRoute.setText(route);
  }

  @Override
  public void setRedColorText(int color) {
    tvRoute.setTextColor(color);
  }

  @Override
  public void setGreenColorText(int color) {
    tvRoute.setTextColor(color);
  }

  @Override
  public void setRedText(String redText) {
    tvRoute.setText(redText);
  }

  @Override
  public void setGreenText(String greenText) {
    tvRoute.setText(greenText);
  }
}

package com.ianarbuckle.dublinbushelper.transports.irishrail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ianarbuckle.dublinbushelper.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 29/07/2017.
 *
 */

public class RailCardViewHolder extends RecyclerView.ViewHolder implements RailCardRowView {

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

  public RailCardViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
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
  public void setCommuterText(String text) {
    tvRoute.setText(text);
  }

  @Override
  public void setIntercityText(String text) {
    tvRoute.setText(text);
  }

  @Override
  public void setDartCommuterText(String text) {
    tvRoute.setText(text);
  }

  @Override
  public void setInterDartCommuter(String text) {
    tvRoute.setText(text);
  }

  @Override
  public void setCommuterInter(String text) {
    tvRoute.setText(text);
  }
}

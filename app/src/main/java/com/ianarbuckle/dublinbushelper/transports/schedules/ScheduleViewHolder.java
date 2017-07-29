package com.ianarbuckle.dublinbushelper.transports.schedules;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ianarbuckle.dublinbushelper.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 27/07/2017.
 *
 */

public class ScheduleViewHolder extends RecyclerView.ViewHolder implements ScheduleRowView {

  @BindView(R.id.tvDestination)
  TextView tvDest;

  @BindView(R.id.tvDue)
  TextView tvDue;

  @BindView(R.id.tvDirection)
  TextView tvDirection;

  public ScheduleViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  @Override
  public void setDestination(String destination) {
    tvDest.setText(destination);
  }

  @Override
  public void setDueTime(String dueTime) {
    tvDue.setText(dueTime);
  }

  @Override
  public void setDirection(String direction) {
    tvDirection.setText(direction);
  }
}

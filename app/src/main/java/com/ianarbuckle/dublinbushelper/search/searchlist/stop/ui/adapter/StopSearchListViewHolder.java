package com.ianarbuckle.dublinbushelper.search.searchlist.stop.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ianarbuckle.dublinbushelper.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 14/10/2017.
 *
 */

public class StopSearchListViewHolder extends RecyclerView.ViewHolder implements StopSearchListViewRow {

  @BindView(R.id.tvStopId)
  TextView tvStopId;

  @BindView(R.id.tvStopName)
  TextView tvStopName;

  public StopSearchListViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  @Override
  public void setStopId(String text) {
    tvStopId.setText(text);
  }

  @Override
  public void setStopName(String text) {
    tvStopName.setText(text);
  }
}

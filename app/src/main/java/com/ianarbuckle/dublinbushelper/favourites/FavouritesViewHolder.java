package com.ianarbuckle.dublinbushelper.favourites;

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

public class FavouritesViewHolder extends RecyclerView.ViewHolder {

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

}

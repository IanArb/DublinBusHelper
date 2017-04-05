package com.ianarbuckle.dublinbushelper.utils;

import android.support.v7.widget.RecyclerView;

import com.ianarbuckle.dublinbushelper.models.stopinfo.Result;

/**
 * Created by Ian Arbuckle on 03/04/2017.
 *
 */

public interface OnRecyclerItemClickListener {
  void onItemClick(RecyclerView.Adapter adapter, Result result, int position);
}

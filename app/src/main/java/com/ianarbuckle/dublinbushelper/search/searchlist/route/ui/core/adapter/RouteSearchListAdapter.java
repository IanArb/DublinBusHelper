package com.ianarbuckle.dublinbushelper.search.searchlist.route.ui.core.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.models.routeinformation.Result;
import com.ianarbuckle.dublinbushelper.models.routeinformation.RouteStop;

import java.util.List;

/**
 * Created by Ian Arbuckle on 02/11/2017.
 *
 */

public class RouteSearchListAdapter extends RecyclerView.Adapter {

  private List<Result> results;

  public RouteSearchListAdapter(List<Result> results) {
    this.results = results;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_list, parent, false);
    return new RouteSearchListViewHolder(view);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    Result result = results.get(position);
    String origin = result.getOrigin();
    String destination = result.getDestination();

    RouteSearchListViewRow viewHolder = (RouteSearchListViewHolder) holder;
    viewHolder.setDestination(destination);
    viewHolder.setOrigin(origin);
  }

  @Override
  public int getItemCount() {
    if(results == null) {
      return 0;
    }
    return results.size();
  }
}

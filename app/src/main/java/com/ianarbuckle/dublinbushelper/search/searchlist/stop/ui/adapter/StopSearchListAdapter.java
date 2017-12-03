package com.ianarbuckle.dublinbushelper.search.searchlist.stop.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.models.stopinfo.Result;
import com.ianarbuckle.dublinbushelper.utils.OnRecyclerItemClickListener;

import java.util.List;


/**
 * Created by Ian Arbuckle on 14/10/2017.
 *
 */

public class StopSearchListAdapter extends RecyclerView.Adapter {
  private final List<Result> results;
  private OnRecyclerItemClickListener onRecyclerItemClickListener;

  public StopSearchListAdapter(List<Result> results) {
    this.results = results;
  }

  @Override
  public StopSearchListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_list_stops, parent, false);
    return new StopSearchListViewHolder(view);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    Result result = results.get(position);

    StopSearchListViewRow viewHolder = (StopSearchListViewHolder) holder;

    String stopId = result.getStopid();
    String stopName = result.getFullname();

    viewHolder.setStopId(stopId);
    viewHolder.setStopName(stopName);

    holder.itemView.setOnClickListener(view -> {
      if(onRecyclerItemClickListener != null) {
        onRecyclerItemClickListener.onItemClick(this, results.get(position), holder.getAdapterPosition());
      }
    });

  }

  @Override
  public int getItemCount() {
    return results.size();
  }

  public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemLongClickListener) {
    this.onRecyclerItemClickListener = onRecyclerItemLongClickListener;
  }

}

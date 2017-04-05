package com.ianarbuckle.dublinbushelper.transports.irishrail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.transports.schedules.ScheduleActivity;
import com.ianarbuckle.dublinbushelper.models.stopinfo.Result;
import com.ianarbuckle.dublinbushelper.utils.Constants;
import com.ianarbuckle.dublinbushelper.utils.OnRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 01/04/2017.
 *
 */

public class RailAdapter extends RecyclerView.Adapter<RailAdapter.RailCardViewHolder> implements Filterable {

  private List<Result> resultList;
  private List<Result> filteredResultList;

  private ResultsFilter resultsFilter;

  private Context context;

  private OnRecyclerItemClickListener onRecyclerItemClickListener;

  public RailAdapter(Context context, List<Result> resultList) {
    this.context = context;
    this.resultList = resultList;
    this.filteredResultList = new ArrayList<>();
  }

  @Override
  public RailCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.layout_card, parent, false);
    return new RailCardViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final RailCardViewHolder holder, int position) {
    final Result result = resultList.get(position);

    TextView tvTime = holder.tvTime;
    TextView tvStopId = holder.tvStopId;
    TextView tvName = holder.tvName;
    TextView tvRoute = holder.tvRoute;
    Button btnSchedule = holder.btnSchedule;
    ImageButton ibFavourite = holder.ibFavourite;

    String lastUpdate = result.getLastupdated();
    String displaystopid = result.getStopid();
    String stopName = result.getDisplaystopid();
    String routes = result.getOperators().get(0).getRoutes().toString();

    setOnClickListener(holder, result, btnSchedule, ibFavourite);

    getRoutes(tvRoute, routes);

    tvTime.setText(lastUpdate);
    tvStopId.setText(displaystopid);
    tvName.setText(stopName);

    holder.setIsRecyclable(true);
  }

  private void setOnClickListener(final RailCardViewHolder holder, final Result result, Button btnSchedule, ImageButton ibFavourite) {
    getScheduleOnClick(holder, btnSchedule);
    ibFavourite.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(onRecyclerItemClickListener != null) {
          onRecyclerItemClickListener.onItemClick(RailAdapter.this, result, holder.getAdapterPosition());
        }
      }
    });

  }

  private void getScheduleOnClick(final RailCardViewHolder holder, Button btnSchedule) {
    btnSchedule.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String stopId = resultList.get(holder.getAdapterPosition()).getStopid();
        String displayName = resultList.get(holder.getAdapterPosition()).getDisplaystopid();
        float latitude = resultList.get(holder.getAdapterPosition()).getLatitude();
        float longtitude = resultList.get(holder.getAdapterPosition()).getLongitude();

        Intent intent = ScheduleActivity.newIntent(context);
        intent.putExtra(Constants.STOPID_KEY, stopId);
        intent.putExtra(Constants.LAT_KEY, latitude);
        intent.putExtra(Constants.LONG_KEY, longtitude);
        intent.putExtra(Constants.DISPLAYNAME_KEY, displayName);
        context.startActivity(intent);
      }
    });
  }

  private void getRoutes(TextView tvRoute, String routes) {
    switch (routes) {
      case Constants.COMMUTER_ID:
        tvRoute.setText(context.getString(R.string.commuter_label));
        break;
      case Constants.INTERCITY_ID:
        tvRoute.setText(context.getString(R.string.intercity_label));
        break;
      case Constants.COMMUTER_DART_ID:
        tvRoute.setText(context.getString(R.string.dart_commuter_label));
        break;
      case Constants.DART_COMMUTER_ID:
        tvRoute.setText(context.getString(R.string.dart_commuter_label));
        break;
      case Constants.COMMUTER_INTER_DART_ID:
        tvRoute.setText(context.getString(R.string.intercity_dart_commuter_label));
        break;
      case Constants.COMMUTER_INTER_ID:
        tvRoute.setText(context.getString(R.string.commuter_intercity_label));
        break;
      case Constants.INTER_COMMUTER_ID:
        tvRoute.setText(context.getString(R.string.commuter_intercity_label));
        break;
      case Constants.INTER_COMMUTER_DART_ID:
        tvRoute.setText(context.getString(R.string.intercity_dart_commuter_label));
        break;
    }
  }

  @Override
  public int getItemCount() {
    return resultList.size();
  }

  @Override
  public Filter getFilter() {
    if(resultsFilter == null) {
      resultsFilter = new ResultsFilter(this, resultList);
    }
    return resultsFilter;
  }

  public void updateList(List<Result> list) {
    filteredResultList = list;
    notifyDataSetChanged();
  }

  private class ResultsFilter extends Filter {

    private final RailAdapter adapter;
    private final List<Result> resultList;
    private final List<Result> filteredList;

    public ResultsFilter(RailAdapter adapter, List<Result> resultList) {
      super();
      this.adapter = adapter;
      this.resultList = new LinkedList<>(resultList);
      this.filteredList = new ArrayList<>();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
      filteredList.clear();
      final FilterResults result = new FilterResults();

      if(constraint.length() == 0) {
        filteredList.addAll(resultList);
      } else {
        final String filterPattern = constraint.toString().trim();

        for(final Result results : resultList) {
          boolean isName = results.getDisplaystopid().contains(filterPattern);
          boolean isOperator = results.getOperators().get(0).getRoutes().toString().contains(filterPattern);
          boolean isStopId = results.getStopid().contains(filterPattern);
          if (isName || isOperator || isStopId) {
            filteredList.add(results);
          }
        }
      }
      result.values = filteredList;
      result.count = filteredList.size();
      return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
      adapter.filteredResultList.clear();
      adapter.filteredResultList.addAll((ArrayList<Result>) results.values);
      adapter.notifyDataSetChanged();
    }
  }

  public static class RailCardViewHolder extends RecyclerView.ViewHolder {

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

    public RailCardViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  public void setOnRecyclerItemLongClickListener(OnRecyclerItemClickListener onRecyclerItemLongClickListener) {
    this.onRecyclerItemClickListener = onRecyclerItemLongClickListener;
  }
}

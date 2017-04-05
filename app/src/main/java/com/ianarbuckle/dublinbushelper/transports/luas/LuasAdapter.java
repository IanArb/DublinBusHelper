package com.ianarbuckle.dublinbushelper.transports.luas;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
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
 * Created by Ian Arbuckle on 02/03/2017.
 *
 */

public class LuasAdapter extends RecyclerView.Adapter<LuasAdapter.LuasCardViewHolder> implements Filterable {

  private List<Result> resultList;

  private List<Result> filteredResultList;

  private Context context;

  private OnRecyclerItemClickListener onRecyclerItemClickListener;

  private ResultsFilter resultsFilter;

  public LuasAdapter(List<Result> resultList, Context context) {
    this.resultList = resultList;
    this.context = context;
    this.filteredResultList = new ArrayList<>();
  }

  @Override
  public LuasCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card, parent, false);
    return new LuasCardViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final LuasCardViewHolder holder, int position) {
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

    setClickListeners(holder, result, btnSchedule, ibFavourite);

    tvTime.setText(lastUpdate);
    tvStopId.setText(displaystopid);
    tvName.setText(stopName);

    setRoutes(tvRoute, routes);

    holder.setIsRecyclable(true);

  }

  private void setClickListeners(final LuasCardViewHolder holder, final Result result, Button btnSchedule, ImageButton ibFavourite) {
    getScheduleOnClick(result, btnSchedule);

    getFavouritesOnClick(holder, result, ibFavourite);
  }

  private void getScheduleOnClick(final Result result, Button btnSchedule) {
    btnSchedule.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String stopId = result.getStopid();
        String displayName = result.getDisplaystopid();
        float latitude = result.getLatitude();
        float longtitude = result.getLongitude();

        Intent intent = ScheduleActivity.newIntent(context);
        intent.putExtra(Constants.STOPID_KEY, stopId);
        intent.putExtra(Constants.LAT_KEY, latitude);
        intent.putExtra(Constants.LONG_KEY, longtitude);
        intent.putExtra(Constants.DISPLAYNAME_KEY, displayName);
        context.startActivity(intent);
      }
    });
  }

  private void getFavouritesOnClick(final LuasCardViewHolder holder, final Result result, ImageButton ibFavourite) {
    ibFavourite.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(onRecyclerItemClickListener != null) {
          onRecyclerItemClickListener.onItemClick(LuasAdapter.this, result, holder.getAdapterPosition());
        }
      }
    });
  }

  private void setRoutes(TextView tvRoute, String routes) {
    switch (routes) {
      case Constants.RED_LINE:
        tvRoute.setText(context.getString(R.string.luas_red));
        tvRoute.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        break;
      case Constants.GREEN_LINE:
        tvRoute.setText(context.getString(R.string.luas_green));
        tvRoute.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));
        break;
      default:
        tvRoute.setText(routes);
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

    private final LuasAdapter adapter;
    private final List<Result> resultList;
    private final List<Result> filteredList;

    public ResultsFilter(LuasAdapter adapter, List<Result> resultList) {
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

  public static class LuasCardViewHolder extends RecyclerView.ViewHolder {

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

  }

  public void setOnRecyclerItemLongClickListener(OnRecyclerItemClickListener onRecyclerItemLongClickListener) {
    this.onRecyclerItemClickListener = onRecyclerItemLongClickListener;
  }
}

package com.ianarbuckle.dublinbushelper.transports.irishrail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.transports.schedules.ScheduleActivity;
import com.ianarbuckle.dublinbushelper.models.stopinfo.Result;
import com.ianarbuckle.dublinbushelper.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 01/04/2017.
 *
 */

public class RailAdapter extends RecyclerView.Adapter<RailAdapter.RailCardViewHolder> {

  private List<Result> resultList;

  private Context context;

  public RailAdapter(Context context, List<Result> resultList) {
    this.context = context;
    this.resultList = resultList;
  }

  @Override
  public RailCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.layout_card, parent, false);
    return new RailCardViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final RailCardViewHolder holder, int position) {
    Result result = resultList.get(position);

    TextView tvTime = holder.tvTime;
    TextView tvStopId = holder.tvStopId;
    TextView tvName = holder.tvName;
    TextView tvRoute = holder.tvRoute;
    Button btnSchedule = holder.btnSchedule;

    String lastUpdate = result.getLastupdated();
    String displaystopid = result.getStopid();
    String stopName = result.getDisplaystopid();
    String routes = result.getOperators().get(0).getRoutes().toString();

    setOnClickListener(holder, btnSchedule);

    getRoutes(tvRoute, routes);

    tvTime.setText(lastUpdate);
    tvStopId.setText(displaystopid);
    tvName.setText(stopName);

    holder.setIsRecyclable(true);
  }

  private void setOnClickListener(final RailCardViewHolder holder, Button btnSchedule) {
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

    public RailCardViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}

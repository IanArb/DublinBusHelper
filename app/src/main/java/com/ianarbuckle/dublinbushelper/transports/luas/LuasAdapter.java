package com.ianarbuckle.dublinbushelper.transports.luas;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
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
 * Created by Ian Arbuckle on 02/03/2017.
 *
 */

public class LuasAdapter extends RecyclerView.Adapter<LuasAdapter.LuasCardViewHolder> {

  private List<Result> resultList;

  Context context;

  @Override
  public LuasCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card, parent, false);
    return new LuasCardViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final LuasCardViewHolder holder, int position) {
    TextView tvTime = holder.tvTime;
    TextView tvStopId = holder.tvStopId;
    TextView tvName = holder.tvName;
    TextView tvRoute = holder.tvRoute;
    Button btnSchedule = holder.btnSchedule;

    String lastUpdate = resultList.get(position).getLastupdated();
    String displaystopid = resultList.get(position).getStopid();
    String stopName = resultList.get(position).getDisplaystopid();
    String routes = resultList.get(position).getOperators().get(0).getRoutes().toString();

    btnSchedule.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String stopId = resultList.get(holder.getAdapterPosition()).getStopid();
        String displayName = resultList.get(holder.getAdapterPosition()).getDisplaystopid();
        float latitude = resultList.get(holder.getAdapterPosition()).getLatitude();
        float longtitude = resultList.get(holder.getAdapterPosition()).getLongitude();

        Intent intent = ScheduleActivity.newIntent(context);
        intent.putExtra("stopId", stopId);
        intent.putExtra("lat", latitude);
        intent.putExtra("long", longtitude);
        intent.putExtra("displayName", displayName);
        context.startActivity(intent);
      }
    });


    tvTime.setText(lastUpdate);
    tvStopId.setText(displaystopid);
    tvName.setText(stopName);

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

    holder.setIsRecyclable(true);

  }

  public LuasAdapter(List<Result> resultList, Context context) {
    this.resultList = resultList;
    this.context = context;
  }

  @Override
  public int getItemCount() {
    return resultList.size();
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

    public LuasCardViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

  }
}

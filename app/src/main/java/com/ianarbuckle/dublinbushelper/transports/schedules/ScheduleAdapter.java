package com.ianarbuckle.dublinbushelper.transports.schedules;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.models.realtimestopinfo.Result;
import com.ianarbuckle.dublinbushelper.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 31/03/2017.
 *
 */

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

  private Context context;

  private List<Result> resultList;

  public ScheduleAdapter(Context context, List<Result> resultList) {
    this.context = context;
    this.resultList = resultList;
  }

  @Override
  public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.layout_item_schedule_data, parent, false);
    return new ScheduleViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ScheduleViewHolder holder, int position) {
    Result result = resultList.get(position);

    String dest = result.getDestination();
    String direction = result.getDirection();
    String dueTime = result.getDuetime();

    TextView tvDest = holder.tvDest;
    TextView tvDirection = holder.tvDirection;
    TextView tvDueTime = holder.tvDue;
    ImageButton ibAlarm = holder.ibAlarm;

    tvDest.setText(dest);
    tvDirection.setText(direction);

    ibAlarm.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(context, "Added to reminder", Toast.LENGTH_SHORT).show();
      }
    });

    switch (dueTime) {
      case Constants.DUE_TIME_ID:
        tvDueTime.setText(dueTime);
        ibAlarm.setVisibility(View.GONE);
        break;
      default:
        String format = String.format(Constants.FORMAT_TIME, dueTime);
        tvDueTime.setText(format);
        break;
    }

  }

  @Override
  public int getItemCount() {
    return resultList.size();
  }


  public static class ScheduleViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tvDestination)
    TextView tvDest;

    @BindView(R.id.tvDue)
    TextView tvDue;

    @BindView(R.id.tvDirection)
    TextView tvDirection;

    @BindView(R.id.ibAlarm)
    ImageButton ibAlarm;

    public ScheduleViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

}

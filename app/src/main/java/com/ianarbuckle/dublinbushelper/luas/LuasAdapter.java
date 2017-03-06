package com.ianarbuckle.dublinbushelper.luas;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.models.busstopinfo.Operator;
import com.ianarbuckle.dublinbushelper.models.busstopinfo.Result;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 02/03/2017.
 *
 */

public class LuasAdapter extends RecyclerView.Adapter<LuasAdapter.LuasCardViewHolder> {

  private List<Result> resultList;

  public static class LuasCardViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tvTime)
    TextView tvTime;

    @BindView(R.id.tvStopid)
    TextView tvStopId;

    @BindView(R.id.tvName)
    TextView tvName;

    public LuasCardViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

  }

  @Override
  public LuasCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_luas, parent, false);
    return new LuasCardViewHolder(view);
  }

  @Override
  public void onBindViewHolder(LuasCardViewHolder holder, int position) {
    TextView tvTime = holder.tvTime;
    TextView tvStopId = holder.tvStopId;
    TextView tvName = holder.tvName;

    for(final Result results : resultList) {
      for(final Operator operators : results.getOperators()) {
        boolean isLuas = operators.getName().contains("LUAS");
        if(isLuas) {
          String lastUpdate = results.getLastupdated();
          tvTime.setText(lastUpdate);
          String displayStopId = results.getStopid();
          tvStopId.setText(displayStopId);
          String fullName = results.getFullname();
          tvName.setText(fullName);
        }
      }
    }

    holder.setIsRecyclable(true);

  }

  public LuasAdapter(List<Result> resultList) {
    this.resultList = resultList;
  }

  @Override
  public int getItemCount() {
    return resultList.size();
  }
}

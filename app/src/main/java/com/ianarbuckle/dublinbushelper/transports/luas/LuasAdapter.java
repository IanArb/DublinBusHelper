package com.ianarbuckle.dublinbushelper.transports.luas;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;

import com.ianarbuckle.dublinbushelper.R;

import com.ianarbuckle.dublinbushelper.models.stopinfo.Result;
import com.ianarbuckle.dublinbushelper.utils.OnRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ian Arbuckle on 02/03/2017.
 *
 */

public class LuasAdapter extends RecyclerView.Adapter<LuasCardViewHolder> implements Filterable {

  private List<Result> filteredResultList;

  private OnRecyclerItemClickListener onRecyclerItemClickListener;

  private ResultsFilter resultsFilter;

  private LuasPresenterImpl presenter;

  public LuasAdapter(LuasPresenterImpl presenter) {
    this.presenter = presenter;
    this.filteredResultList = new ArrayList<>();
  }

  @Override
  public LuasCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card, parent, false);
    return new LuasCardViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final LuasCardViewHolder holder, final int position) {
    presenter.onBindRowViewAtPositon(position, holder);
    presenter.setRouteText(position, holder);

    Button btnSchedule = holder.btnSchedule;
    ImageButton btnFavourites = holder.ibFavourite;
    onClickListeners(holder, position, btnSchedule, btnFavourites);
  }

  private void onClickListeners(final LuasCardViewHolder holder, final int position, Button btnSchedule, ImageButton btnFavourites) {
    btnSchedule.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        presenter.onRowClickAtPosition(holder.getAdapterPosition(), holder);
      }
    });

    btnFavourites.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(onRecyclerItemClickListener != null) {
          onRecyclerItemClickListener.onItemClick(LuasAdapter.this, presenter.getResults(position), holder.getAdapterPosition());
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return presenter.getResultsRowCount();
  }

  @Override
  public Filter getFilter() {
    if(resultsFilter == null) {
      resultsFilter = new ResultsFilter(this, presenter.getResultsList());
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

  public void setOnRecyclerItemLongClickListener(OnRecyclerItemClickListener onRecyclerItemLongClickListener) {
    this.onRecyclerItemClickListener = onRecyclerItemLongClickListener;
  }
}

package com.ianarbuckle.dublinbushelper.transports.irishrail;

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
 * Created by Ian Arbuckle on 01/04/2017.
 *
 */

public class RailAdapter extends RecyclerView.Adapter<RailCardViewHolder> implements Filterable {

  private List<Result> filteredResultList;
  private ResultsFilter resultsFilter;

  private RailPresenterImpl presenter;

  private OnRecyclerItemClickListener onRecyclerItemClickListener;

  public RailAdapter(RailPresenterImpl presenter) {
    this.presenter = presenter;
    this.filteredResultList = new ArrayList<>();
  }

  @Override
  public RailCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card, parent, false);
    return new RailCardViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final RailCardViewHolder holder, int position) {
    presenter.onBindRowViewAtPositon(position, holder);
    presenter.setRouteText(position, holder);

    Button btnSchedule = holder.btnSchedule;
    ImageButton btnFavourites = holder.ibFavourite;
    onClickListeners(holder, position, btnSchedule, btnFavourites);
  }

  private void onClickListeners(final RailCardViewHolder holder, final int position, Button btnSchedule, ImageButton btnFavourites) {
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
          onRecyclerItemClickListener.onItemClick(RailAdapter.this, presenter.getResults(position), holder.getAdapterPosition());
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



  public void setOnRecyclerItemLongClickListener(OnRecyclerItemClickListener onRecyclerItemLongClickListener) {
    this.onRecyclerItemClickListener = onRecyclerItemLongClickListener;
  }
}

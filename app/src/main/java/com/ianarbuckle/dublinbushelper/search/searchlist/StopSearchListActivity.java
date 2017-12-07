package com.ianarbuckle.dublinbushelper.search.searchlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.ianarbuckle.dublinbushelper.BaseActivity;
import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.TransportHelperApplication;
import com.ianarbuckle.dublinbushelper.models.stopinfo.Result;
import com.ianarbuckle.dublinbushelper.search.SearchPagerActivity;
import com.ianarbuckle.dublinbushelper.search.searchlist.stop.ui.StopSearchListView;
import com.ianarbuckle.dublinbushelper.search.searchlist.stop.ui.adapter.StopSearchListAdapter;
import com.ianarbuckle.dublinbushelper.search.searchlist.stop.ui.builder.DaggerStopSearchListComponent;
import com.ianarbuckle.dublinbushelper.search.searchlist.stop.ui.builder.StopSearchListModule;
import com.ianarbuckle.dublinbushelper.search.searchlist.stop.ui.core.presenter.StopSearchPresenter;
import com.ianarbuckle.dublinbushelper.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */

public class StopSearchListActivity extends BaseActivity implements StopSearchListView {

  @Inject
  StopSearchPresenter presenter;

  @BindView(R.id.recyclerView)
  RecyclerView recyclerView;

  @BindView(R.id.progressBar)
  ProgressBar progressBar;

  @BindView(R.id.progressBarContainer)
  FrameLayout progressBarContainer;

  StopSearchListAdapter adapter;

  public static void init(Activity activity) {
    Intent intent = new Intent(activity, StopSearchListActivity.class);
    activity.startActivityForResult(intent, Constants.REQUEST_CODE);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    DaggerStopSearchListComponent.builder()
        .applicationComponent(TransportHelperApplication.get(this).getApplicationComponent())
        .stopSearchListModule(new StopSearchListModule(this))
        .build()
        .inject(this);
    presenter.onCreate();
  }

  @Override
  protected void initLayout() {
    setContentView(R.layout.layout_search_list);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
  }

  @Override
  public void setResults(List<Result> results) {
    recyclerView.setHasFixedSize(true);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(linearLayoutManager);
    adapter = new StopSearchListAdapter(results);
    recyclerView.setAdapter(adapter);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
    recyclerView.addItemDecoration(dividerItemDecoration);
    setValues();
  }

  private void setValues() {
    adapter.setOnRecyclerItemClickListener((selectionAdapter, result, position) -> {
      String stopid = result.getStopid();
      String fullname = result.getFullname();

      Bundle bundle = new Bundle();
      bundle.putString(Constants.STOPID_KEY, stopid);
      bundle.putString(Constants.NAME_KEY, fullname);

      Intent intent = new Intent(this, SearchPagerActivity.class);
      intent.putExtras(bundle);
      setResult(Activity.RESULT_OK, intent);
      finish();
    });
  }

  @Override
  public void showError() {

  }

  @Override
  public void showProgress() {
    if(progressBar != null) {
      progressBar.setProgress(Constants.PROGRESS_BAR_VALUE);
    }
  }

  @Override
  public void hideProgress() {
    if(progressBar != null) {
      progressBarContainer.setVisibility(View.GONE);
      progressBar.setVisibility(View.GONE);
    }
  }
}

package com.ianarbuckle.dublinbushelper.search.searchlist.route;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ianarbuckle.dublinbushelper.BaseActivity;
import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.TransportHelperApplication;
import com.ianarbuckle.dublinbushelper.models.routeinformation.Result;
import com.ianarbuckle.dublinbushelper.search.searchlist.route.ui.core.adapter.RouteSearchListAdapter;
import com.ianarbuckle.dublinbushelper.search.searchlist.route.ui.core.builder.DaggerRouteSearchListComponent;
import com.ianarbuckle.dublinbushelper.search.searchlist.route.ui.core.builder.RouteSearchListModule;
import com.ianarbuckle.dublinbushelper.search.searchlist.route.ui.core.presenter.RouteSearchListPresenter;
import com.ianarbuckle.dublinbushelper.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Ian Arbuckle on 30/10/2017.
 *
 */

public class RouteSearchListActivity extends BaseActivity implements RouteSearchListView {

  @Inject
  RouteSearchListPresenter presenter;

  @BindView(R.id.recyclerView)
  RecyclerView recyclerView;

  @BindView(R.id.progressBar)
  ProgressBar progressBar;

  @BindView(R.id.progressBarContainer)
  FrameLayout progressBarContainer;

  RouteSearchListAdapter adapter;

  public static void init(Activity activity) {
    Intent intent = new Intent(activity, RouteSearchListActivity.class);
    activity.startActivityForResult(intent, Constants.REQUEST_CODE);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    DaggerRouteSearchListComponent.builder()
        .applicationComponent(TransportHelperApplication.get(this).getApplicationComponent())
        .routeSearchListModule(new RouteSearchListModule(this))
        .build()
        .inject(this);
    presenter.onCreate();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
  }

  @Override
  protected void initLayout() {
    setContentView(R.layout.layout_search_list);
  }

  @Override
  public void setResults(List<Result> results) {
    adapter = new RouteSearchListAdapter(results);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);
  }

  @Override
  public void showProgress() {
    progressBar.setProgress(Constants.PROGRESS_BAR_VALUE);
  }

  @Override
  public void hideProgress() {
    progressBarContainer.setVisibility(View.GONE);
    progressBar.setVisibility(View.GONE);
  }

  @Override
  public void showError() {
    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
  }
}

package com.ianarbuckle.dublinbushelper.search.selection;

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
import com.ianarbuckle.dublinbushelper.models.stopinfo.Result;
import com.ianarbuckle.dublinbushelper.search.SearchPagerActivity;
import com.ianarbuckle.dublinbushelper.search.selection.builder.DefaultSelectionInjector;
import com.ianarbuckle.dublinbushelper.search.selection.builder.SelectionInjector;
import com.ianarbuckle.dublinbushelper.search.selection.ui.SelectionView;
import com.ianarbuckle.dublinbushelper.search.selection.ui.adapter.SelectionAdapter;
import com.ianarbuckle.dublinbushelper.search.selection.ui.core.presenter.SelectionPresenter;
import com.ianarbuckle.dublinbushelper.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */

public class SelectionActivity extends BaseActivity implements SelectionView {

  @Inject
  SelectionPresenter presenter;

  @BindView(R.id.recyclerView)
  RecyclerView recyclerView;

  @BindView(R.id.progressBar)
  ProgressBar progressBar;

  @BindView(R.id.progressBarContainer)
  FrameLayout progressBarContainer;

  SelectionAdapter adapter;

  public static void init(Activity activity) {
    Intent intent = new Intent(activity, SelectionActivity.class);
    activity.startActivityForResult(intent, Constants.REQUEST_CODE);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    injectDagger();
    presenter.onCreate();
  }

  @Override
  protected void initLayout() {
    setContentView(R.layout.layout_selection);
  }

  private void injectDagger() {
    SelectionInjector selectionInjector = new DefaultSelectionInjector(this);
    selectionInjector.inject(this);
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
    adapter = new SelectionAdapter(results);
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

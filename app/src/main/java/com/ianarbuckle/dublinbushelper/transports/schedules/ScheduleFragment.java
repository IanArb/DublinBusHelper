package com.ianarbuckle.dublinbushelper.transports.schedules;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ianarbuckle.dublinbushelper.BaseFragment;
import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.utils.Constants;
import com.ianarbuckle.dublinbushelper.utils.ErrorDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Ian Arbuckle on 02/03/2017.
 *
 */

public class ScheduleFragment extends BaseFragment implements ScheduleView {

  @BindView(R.id.rv)
  RecyclerView recyclerView;

  LinearLayoutManager linearLayoutManager;

  SchedulePresenterImpl presenter;

  public static Fragment newInstance() {
    return new ScheduleFragment();
  }

  @Override
  protected void initPresenter() {
    presenter = new SchedulePresenterImpl(this);
  }

  @Override
  public void onStart() {
    super.onStart();
    Intent intent = getActivity().getIntent();
    String stopId = intent.getStringExtra(Constants.STOPID_KEY);
    presenter.fetchSchedules(stopId);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_schedule, container, false);
    ButterKnife.bind(this, view);
    recyclerView.setHasFixedSize(true);
    linearLayoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(linearLayoutManager);
    return view;
  }

  @Override
  public void showProgress() {
    showProgressDialog();
  }

  @Override
  public void hideProgress() {
    hideProgressDialog();
  }

  @Override
  public void showErrorMessage() {
    FragmentTransaction fragmentTransaction = getFragmentTransaction();
    DialogFragment dialogFragment = ErrorDialogFragment.newInstance(R.string.error_dialog_title);
    dialogFragment.show(fragmentTransaction, Constants.DIALOG_FRAGMENT);
  }

  @NonNull
  private FragmentTransaction getFragmentTransaction() {
    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
    Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(Constants.DIALOG_FRAGMENT);

    if (fragment != null) {
      fragmentTransaction.remove(fragment);
    }

    fragmentTransaction.addToBackStack(null);
    return fragmentTransaction;
  }

  @Override
  public void setAdapter(ScheduleAdapter adapter) {
    recyclerView.setAdapter(adapter);
  }
}

package com.ianarbuckle.dublinbushelper.transports.dublinbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.transports.schedules.ScheduleActivity;
import com.ianarbuckle.dublinbushelper.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Ian Arbuckle on 20/02/2017.
 *
 */

public class PopupDialogFragment extends DialogFragment {

  Unbinder unbinder;

  String displayStopId;
  String shortName;
  String shortnameLocalised;
  String lastUpdated;
  String routes;

  @BindView(R.id.tvDisplayStopId)
  TextView tvDisplayStopId;

  @BindView(R.id.tvShortname)
  TextView tvShortname;

  @BindView(R.id.tvShortnameLocal)
  TextView tvShortnamelocal;

  @BindView(R.id.tvLastUpdate)
  TextView tvLastupdate;

  @BindView(R.id.tvRoutes)
  TextView tvRoutes;


  public PopupDialogFragment() {

  }

  public static PopupDialogFragment newInstance(String displayStopId, String shortName, String shortnameLocalised, String lastUpdated, String routes) {
    PopupDialogFragment popupDialogFragment = new PopupDialogFragment();
    Bundle args = new Bundle();
    args.putString(Constants.DISPLAY_STOP_ID_KEY, displayStopId);
    args.putString(Constants.SHORT_NAME_KEY, shortName);
    args.putString(Constants.SHORT_NAME_LOCALISED_KEY, shortnameLocalised);
    args.putString(Constants.LAST_UPDATED_KEY, lastUpdated);
    args.putString(Constants.ROUTES_KEY, routes);
    popupDialogFragment.setArguments(args);
    return popupDialogFragment;
  }

  @Override
  public void onStart() {
    super.onStart();
    Window window = getDialog().getWindow();
    if(window != null) {
      window.setWindowAnimations(R.style.DialogAnimation);
      int matchParent = WindowManager.LayoutParams.MATCH_PARENT;
      int wrapContent = WindowManager.LayoutParams.WRAP_CONTENT;
      window.setLayout(matchParent, wrapContent);
      int bottom = Gravity.BOTTOM;
      window.setGravity(bottom);
    }
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_popup, container, false);

    ButterKnife.bind(this, view);

    butterKnifeUnbinder(view);

    initViews();

    onCloseClick();

    return view;
  }

  private void butterKnifeUnbinder(View view) {
    unbinder = ButterKnife.bind(this, view);
  }

  private void initViews() {
    displayStopId = getArguments().getString(Constants.DISPLAY_STOP_ID_KEY);
    shortName = getArguments().getString(Constants.SHORT_NAME_KEY);
    shortnameLocalised = getArguments().getString(Constants.SHORT_NAME_LOCALISED_KEY);
    lastUpdated = getArguments().getString(Constants.LAST_UPDATED_KEY);
    routes = getArguments().getString(Constants.ROUTES_KEY);

    tvDisplayStopId.setText(displayStopId);
    tvShortname.setText(shortName);
    tvShortnamelocal.setText(shortnameLocalised);
    tvLastupdate.setText(lastUpdated);
    tvRoutes.setText(routes);
  }

  @OnClick(R.id.ibClose)
  public void onCloseClick() {
    getDialog().dismiss();
  }

  @OnClick(R.id.btnSchedule)
  public void onShowScheduleClick() {
    displayStopId = getArguments().getString(Constants.DISPLAY_STOP_ID_KEY);
    shortName = getArguments().getString(Constants.SHORT_NAME_KEY);
    Intent intent = ScheduleActivity.newIntent(getContext());
    intent.putExtra(Constants.DISPLAYNAME_KEY, shortName);
    intent.putExtra(Constants.STOPID_KEY, displayStopId);
    startActivity(intent);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
}

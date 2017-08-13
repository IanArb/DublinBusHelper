package com.ianarbuckle.dublinbushelper.transports.dublinbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.TransportHelperApplication;
import com.ianarbuckle.dublinbushelper.transports.dublinbus.di.DaggerDublinBusComponent;
import com.ianarbuckle.dublinbushelper.transports.dublinbus.di.DublinBusModule;
import com.ianarbuckle.dublinbushelper.transports.dublinbus.di.HelperModule;
import com.ianarbuckle.dublinbushelper.transports.schedules.ScheduleActivity;
import com.ianarbuckle.dublinbushelper.utils.Constants;
import com.ianarbuckle.dublinbushelper.utils.ErrorDialogFragment;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Ian Arbuckle on 20/02/2017.
 *
 */

public class PopupDialogFragment extends DialogFragment implements DialogCallback {
  Unbinder unbinder;

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

  @Inject @Named("dialogPresenter")
  DublinBusPresenterImpl presenter;


  public PopupDialogFragment() {

  }

  public static PopupDialogFragment newInstance(String displayStopId, String shortName, String shortnameLocalised, String lastUpdated, String routes, float latitude, float longtitude) {
    PopupDialogFragment popupDialogFragment = new PopupDialogFragment();
    Bundle args = new Bundle();
    args.putString(Constants.DISPLAY_STOP_ID_KEY, displayStopId);
    args.putString(Constants.SHORT_NAME_KEY, shortName);
    args.putString(Constants.SHORT_NAME_LOCALISED_KEY, shortnameLocalised);
    args.putString(Constants.LAST_UPDATED_KEY, lastUpdated);
    args.putString(Constants.ROUTES_KEY, routes);
    args.putFloat(Constants.LAT_KEY, latitude);
    args.putFloat(Constants.LONG_KEY, longtitude);
    popupDialogFragment.setArguments(args);
    return popupDialogFragment;
  }

  @Override
  public void onStart() {
    injectDagger();
    super.onStart();
    drawWindow();
  }

  private void injectDagger() {
    DaggerDublinBusComponent.builder()
        .applicationComponent(TransportHelperApplication.getApplicationComponent(getContext()))
        .dublinBusModule(new DublinBusModule(this))
        .helperModule(new HelperModule())
        .build()
        .inject(this);
  }

  private void drawWindow() {
    Window window = getDialog().getWindow();
    if (window != null) {
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

  private void initViews() {
    String displayStopId = getArguments().getString(Constants.DISPLAY_STOP_ID_KEY);
    String shortName = getArguments().getString(Constants.SHORT_NAME_KEY);
    String shortnameLocalised = getArguments().getString(Constants.SHORT_NAME_LOCALISED_KEY);
    String lastUpdated = getArguments().getString(Constants.LAST_UPDATED_KEY);
    String routes = getArguments().getString(Constants.ROUTES_KEY);

    tvDisplayStopId.setText(displayStopId);
    tvShortname.setText(shortName);
    tvShortnamelocal.setText(shortnameLocalised);
    tvLastupdate.setText(lastUpdated);
    tvRoutes.setText(routes);
  }

  private void butterKnifeUnbinder(View view) {
    unbinder = ButterKnife.bind(this, view);
  }

  @OnClick(R.id.ibClose)
  public void onCloseClick() {
    getDialog().dismiss();
  }

  @OnClick(R.id.btnSchedule)
  public void onShowScheduleClick() {
    String displayStopId = getArguments().getString(Constants.DISPLAY_STOP_ID_KEY);
    String shortName = getArguments().getString(Constants.SHORT_NAME_KEY);
    float latitude = getArguments().getFloat(Constants.LAT_KEY);
    float longtitude = getArguments().getFloat(Constants.LONG_KEY);

    Intent intent = ScheduleActivity.Companion.newIntent(getContext());
    intent.putExtra(Constants.DISPLAYNAME_KEY, shortName);
    intent.putExtra(Constants.STOPID_KEY, displayStopId);
    intent.putExtra(Constants.LAT_KEY, latitude);
    intent.putExtra(Constants.LONG_KEY, longtitude);
    getActivity().startActivity(intent);
  }

  @OnClick(R.id.ibFavourite)
  public void onFavouriteClick() {
    String displayStopId = getArguments().getString(Constants.DISPLAY_STOP_ID_KEY);
    String shortName = getArguments().getString(Constants.SHORT_NAME_KEY);
    String lastUpdated = getArguments().getString(Constants.LAST_UPDATED_KEY);
    String routes = getArguments().getString(Constants.ROUTES_KEY);
    float latitude = getArguments().getFloat(Constants.LAT_KEY);
    float longtitude = getArguments().getFloat(Constants.LONG_KEY);
    presenter.sendToDatabase(shortName, latitude, longtitude, lastUpdated, displayStopId, routes);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override
  public void onSuccess() {
    Toast.makeText(getContext(), "Saved to favourites", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onFailure() {
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

}

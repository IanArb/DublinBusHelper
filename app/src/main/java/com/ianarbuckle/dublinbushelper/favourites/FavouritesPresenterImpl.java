package com.ianarbuckle.dublinbushelper.favourites;

import android.content.Intent;

import com.ianarbuckle.dublinbushelper.models.Favourites;
import com.ianarbuckle.dublinbushelper.transports.schedules.ScheduleActivity;
import com.ianarbuckle.dublinbushelper.utils.Constants;

/**
 * Created by Ian Arbuckle on 30/07/2017.
 *
 */

public class FavouritesPresenterImpl implements FavouritesPresenter {
  private FavouritesView view;

  public FavouritesPresenterImpl(FavouritesView view) {
    this.view = view;
  }

  @Override
  public void onBindRowViewAtPosition(Favourites model, int position, FavouritesCardRowView view) {
    String routes = model.getRoutes();
    String name = model.getName();
    String stopId = model.getStopId();
    String time = model.getTime();

    view.setName(name);
    view.setRoute(routes);
    view.setStopId(stopId);
    view.setTime(time);
  }

  @Override
  public void onRowClickPosition(Favourites model, int position, FavouritesCardRowView cardView) {
    String stopId = model.getStopId();
    String displayName = model.getName();
    float latitude = model.getLatitude();
    float longtitude = model.getLongitude();

    Intent intent = ScheduleActivity.Companion.newIntent(view.getContext());
    intent.putExtra(Constants.STOPID_KEY, stopId);
    intent.putExtra(Constants.LAT_KEY, latitude);
    intent.putExtra(Constants.LONG_KEY, longtitude);
    intent.putExtra(Constants.DISPLAYNAME_KEY, displayName);
    view.getContext().startActivity(intent);
  }

  @Override
  public void setRouteText(Favourites model, int position, FavouritesViewHolder view) {
    switch (model.getRoutes()) {
      case Constants.GREEN_LINE:
        view.setGreenText();
        view.setGreenColorText();
        break;
      case Constants.RED_LINE:
        view.setRedText();
        view.setRedColorText();
        break;
      case Constants.COMMUTER_ID:
        view.setCommuterText();
        break;
      case Constants.INTERCITY_ID:
        view.setIntercityText();
        break;
      case Constants.COMMUTER_DART_ID:
        view.setDartCommuterText();
        break;
      case Constants.DART_COMMUTER_ID:
        view.setDartCommuterText();
        break;
      case Constants.COMMUTER_INTER_DART_ID:
        view.setDartCommuterText();
        break;
      case Constants.COMMUTER_INTER_ID:
        view.setCommuterInter();
        break;
      case Constants.INTER_COMMUTER_ID:
        view.setCommuterInter();
        break;
    }
  }
}

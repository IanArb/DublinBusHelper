package com.ianarbuckle.dublinbushelper.favourites;

import com.ianarbuckle.dublinbushelper.models.Favourites;

/**
 * Created by Ian Arbuckle on 29/07/2017.
 *
 */

public interface FavouritesPresenter {
  void onBindRowViewAtPosition(Favourites model, int position, FavouritesCardRowView view);
  void onRowClickPosition(Favourites model, int position, FavouritesCardRowView cardView);
  void setRouteText(Favourites model, int position, FavouritesViewHolder view);
}

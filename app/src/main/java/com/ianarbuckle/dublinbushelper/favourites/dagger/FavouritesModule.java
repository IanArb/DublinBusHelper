package com.ianarbuckle.dublinbushelper.favourites.dagger;

import com.ianarbuckle.dublinbushelper.favourites.FavouritesPresenterImpl;
import com.ianarbuckle.dublinbushelper.favourites.FavouritesView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ian Arbuckle on 08/08/2017.
 *
 */
@Module
public class FavouritesModule {
  private FavouritesView view;

  public FavouritesModule(FavouritesView view) {
    this.view = view;
  }

  @FavouritesScope
  @Provides
  FavouritesView provideView() {
    return view;
  }

  @FavouritesScope
  @Provides
  FavouritesPresenterImpl providesPresenter() {
    return new FavouritesPresenterImpl(view);
  }
}

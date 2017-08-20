package com.ianarbuckle.dublinbushelper.favourites.dagger;

import com.ianarbuckle.dublinbushelper.ApplicationComponent;
import com.ianarbuckle.dublinbushelper.favourites.FavouritesFragment;

import dagger.Component;

/**
 * Created by Ian Arbuckle on 08/08/2017.
 *
 */
@FavouritesScope
@Component(modules = FavouritesModule.class, dependencies = ApplicationComponent.class)
public interface FavouritesComponent {
  void inject(FavouritesFragment fragment);
}

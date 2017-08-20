package com.ianarbuckle.dublinbushelper.transports.luas.dagger;

import com.ianarbuckle.dublinbushelper.firebase.database.DatabaseHelper;
import com.ianarbuckle.dublinbushelper.network.NetworkClient;
import com.ianarbuckle.dublinbushelper.transports.luas.LuasPresenterImpl;
import com.ianarbuckle.dublinbushelper.transports.luas.LuasView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ian Arbuckle on 12/08/2017.
 *
 */
@Module
public class LuasModule {
  private LuasView view;

  public LuasModule(LuasView view) {
    this.view = view;
  }

  @Provides
  LuasView provideView() {
    return view;
  }

  @Provides
  LuasPresenterImpl providesPresenter(LuasView view, DatabaseHelper databaseHelper, NetworkClient networkClient) {
    return new LuasPresenterImpl(view, databaseHelper, networkClient);
  }
}

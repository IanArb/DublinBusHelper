package com.ianarbuckle.dublinbushelper.transports.luas.di;

import com.ianarbuckle.dublinbushelper.TransportHelperApplication;
import com.ianarbuckle.dublinbushelper.firebase.database.DatabaseHelper;
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
  LuasPresenterImpl providesPresenter(LuasView view, DatabaseHelper databaseHelper) {
    return new LuasPresenterImpl(view, databaseHelper);
  }

  @Provides
  DatabaseHelper provideDatabaseHelper() {
    return TransportHelperApplication.getAppInstance().getDatabaseHelper();
  }
}

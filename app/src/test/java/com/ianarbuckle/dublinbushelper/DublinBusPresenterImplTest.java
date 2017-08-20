package com.ianarbuckle.dublinbushelper;

import android.support.v4.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.ianarbuckle.dublinbushelper.firebase.database.DatabaseHelper;
import com.ianarbuckle.dublinbushelper.helper.LocationHelper;
import com.ianarbuckle.dublinbushelper.network.NetworkClient;
import com.ianarbuckle.dublinbushelper.transports.dublinbus.DublinBusPresenterImpl;
import com.ianarbuckle.dublinbushelper.transports.dublinbus.DublinBusView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import rx.Scheduler;
import rx.Subscription;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Ian Arbuckle on 20/08/2017.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class DublinBusPresenterImplTest {

  private DublinBusPresenterImpl presenter;

  private DublinBusPresenterImpl dialogPresenter;

  private GoogleMap map;

  @Mock
  NetworkClient networkClient;

  @Mock
  DublinBusView view;

  @Mock
  DublinBusPresenterImpl.DialogCallback callback;

  @Mock
  DatabaseHelper databaseHelper;

  @Mock
  LocationHelper locationHelper;

  @Mock
  Subscription subscription;

  @Mock
  Fragment fragment;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    presenter = new DublinBusPresenterImpl(networkClient, locationHelper, view);
    dialogPresenter = new DublinBusPresenterImpl(databaseHelper, callback);

    RxJavaHooks.setOnIOScheduler(new Func1<Scheduler, Scheduler>() {
      @Override
      public Scheduler call(Scheduler scheduler) {
        return Schedulers.immediate();
      }
    });

    final RxAndroidPlugins rxAndroidPlugins = RxAndroidPlugins.getInstance();
    rxAndroidPlugins.registerSchedulersHook(new RxAndroidSchedulersHook() {
      @Override
      public Scheduler getMainThreadScheduler() {
        return Schedulers.immediate();
      }
    });
  }

  @After
  public void tearDown() throws Exception {
    RxJavaHooks.reset();
    RxAndroidPlugins.getInstance().reset();
  }

  @Test
  public void testNetworkResponse() {
    when(networkClient.getStopInformation(any(NetworkClient.StopInformationCallback.class), ArgumentMatchers.<String, String>anyMap()))
        .thenReturn(subscription);
    presenter.fetchStops();
    verify(view).showProgress();
  }

  @Test
  public void testLocationPermission() {
    presenter.checkLocationPermission(fragment);
    verify(locationHelper).checkLocationPermission(any(Fragment.class));
  }

  @Test
  public void testRequestPermission() {
    presenter.onRequestPermission();
    verify(locationHelper).onRequestPermission();
  }

  @Test
  public void testIfValuesAreEmpty() {
    dialogPresenter.sendToDatabase("", 0, 0, "", "", "");
    verify(callback).onFailure();
  }

  @Test
  public void testIfValuesAreNull() {
    dialogPresenter.sendToDatabase(null, 0, 0, null, null, null);
    verify(callback).onFailure();
  }

  @Test
  public void testIfValuesAreNotEmptyOrNull() {
    dialogPresenter.sendToDatabase("luas", 2, 2, "August", "LUAS11", "123");
    verify(callback).onSuccess();
  }
}

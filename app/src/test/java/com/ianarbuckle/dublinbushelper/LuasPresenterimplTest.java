package com.ianarbuckle.dublinbushelper;

import com.ianarbuckle.dublinbushelper.firebase.database.DatabaseHelper;
import com.ianarbuckle.dublinbushelper.network.NetworkClient;
import com.ianarbuckle.dublinbushelper.transports.luas.LuasPresenterImpl;
import com.ianarbuckle.dublinbushelper.transports.luas.LuasView;

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
 * Created by Ian Arbuckle on 05/04/2017.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class LuasPresenterimplTest {

  private LuasPresenterImpl presenter;

  @Mock
  NetworkClient networkClient;

  @Mock
  LuasView view;

  @Mock
  DatabaseHelper databaseHelper;

  @Mock
  Subscription subscription;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    presenter = new LuasPresenterImpl(view, databaseHelper, networkClient);

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

}

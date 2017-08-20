package com.ianarbuckle.dublinbushelper;

import com.ianarbuckle.dublinbushelper.helper.LocationHelper;
import com.ianarbuckle.dublinbushelper.network.NetworkClient;
import com.ianarbuckle.dublinbushelper.transports.schedules.SchedulePresenterImpl;
import com.ianarbuckle.dublinbushelper.transports.schedules.ScheduleView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Ian Arbuckle on 05/04/2017.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SchedulePresenterImplTest {

  private SchedulePresenterImpl presenter;

  @Mock
  Subscription subscription;

  @Mock
  ScheduleView view;

  @Mock
  LocationHelper locationHelper;

  @Mock
  NetworkClient networkClient;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    presenter = new SchedulePresenterImpl(locationHelper, view, networkClient);

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
  public void testFetchSchedules() {
    when(networkClient.getRealTimeInformation(any(NetworkClient.RealTimeInformationCallback.class), anyString()))
        .thenReturn(subscription);
    presenter.fetchSchedules("LUAS11");
    verify(view).showProgress();
  }

}

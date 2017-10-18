package com.ianarbuckle.dublinbushelper.search.selection.ui.core.interactor;

import com.ianarbuckle.dublinbushelper.models.stopinfo.StopInformation;
import com.ianarbuckle.dublinbushelper.network.RealTimePassengerInfoAPI;
import com.ianarbuckle.dublinbushelper.utils.Constants;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Ian Arbuckle on 16/10/2017.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectionInteractorTest {

  private SelectionInteractor interactor;

  @Mock
  RealTimePassengerInfoAPI apiService;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);

    interactor = new DefaultSelectionInteractor(apiService);

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
  public void should_fetchStopInformation() {
    final StopInformation stopInformation = new StopInformation();
    when(apiService.getStopInformation(ArgumentMatchers.anyMap())).thenReturn(Observable.just(stopInformation));

    interactor.fetchStopInformation();

    Map<String, String> filterMap = new HashMap<>();
    filterMap.put(Constants.OPERATOR_KEY, Constants.OPERATOR_VALUE_BUS);
    filterMap.put(Constants.FORMAT_KEY, Constants.FORMAT_VALUE);
    verify(apiService, times(1)).getStopInformation(filterMap);
  }

  @Test
  public void should_returnErrorOnFailure() {
    Throwable throwable = new Throwable();
    when(apiService.getStopInformation(ArgumentMatchers.anyMap())).thenReturn(Observable.error(throwable));

    interactor.fetchStopInformation();

    Map<String, String> filterMap = new HashMap<>();
    filterMap.put(Constants.OPERATOR_KEY, Constants.OPERATOR_VALUE_BUS);
    filterMap.put(Constants.FORMAT_KEY, Constants.FORMAT_VALUE);
    verify(apiService, times(1)).getStopInformation(filterMap);
  }

}

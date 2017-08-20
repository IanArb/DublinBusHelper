package com.ianarbuckle.dublinbushelper;

import com.ianarbuckle.dublinbushelper.models.realtimestopinfo.RealTimeInfo;
import com.ianarbuckle.dublinbushelper.models.stopinfo.StopInformation;
import com.ianarbuckle.dublinbushelper.network.APIService;
import com.ianarbuckle.dublinbushelper.network.NetworkClient;
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

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Ian Arbuckle on 20/08/2017.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class TestNetworkClient {

  @Mock
  APIService mockApiService;

  @Mock
  Observable<StopInformation> mockStopInfoCall;

  @Mock
  Observable<RealTimeInfo> mockRealTimeInfoCall;

  @Mock
  NetworkClient.RealTimeInformationCallback mockRealTimeInfoCallback;

  @Mock
  NetworkClient.StopInformationCallback mockStopInfoCallback;

  @Mock
  NetworkClient networkClient;

  private Map<String, String> filterMap;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);

    networkClient = new NetworkClient(mockApiService);
    filterMap = new HashMap<>();

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
  public void testSuccessOnStopInfoCallback() {
    final StopInformation stopInformation = new StopInformation();
    when(mockApiService.getStopInfo(ArgumentMatchers.<String, String>anyMap())).thenReturn(Observable.just(stopInformation));

    filterMap.put(Constants.FORMAT_KEY, Constants.FORMAT_VALUE);
    filterMap.put(Constants.OPERATOR_KEY, Constants.OPERATOR_VALUE_LUAS);

    networkClient.getStopInformation(mockStopInfoCallback, filterMap);

    verify(mockStopInfoCallback, never()).onError();
    verify(mockStopInfoCallback, times(1)).onSuccess(stopInformation);
  }

  @Test
  public void testErrorOnStopInfoCallback() {
    StopInformation stopInformation = new StopInformation();

    Throwable throwable = new Throwable();
    when(mockApiService.getStopInfo(ArgumentMatchers.<String, String>anyMap()))
        .thenReturn(Observable.<StopInformation>error(throwable));

    filterMap.put(Constants.FORMAT_KEY, Constants.FORMAT_VALUE);
    filterMap.put(Constants.OPERATOR_KEY, Constants.OPERATOR_VALUE_LUAS);

    networkClient.getStopInformation(mockStopInfoCallback, filterMap);

    verify(mockStopInfoCallback, never()).onSuccess(stopInformation);
    verify(mockStopInfoCallback, times(1)).onError();
  }

  @Test
  public void testSuccessOnRealTimeInfoCallback() {
    final RealTimeInfo realTimeInfo = new RealTimeInfo();
    when(mockApiService.getRealTimeInfo(ArgumentMatchers.<String, String>anyMap())).thenReturn(Observable.just(realTimeInfo));

    networkClient.getRealTimeInformation(mockRealTimeInfoCallback, "LUAS11");

    verify(mockRealTimeInfoCallback, never()).onError();
    verify(mockRealTimeInfoCallback, times(1)).onSuccess(realTimeInfo);
  }

  @Test
  public void testErrorOnRealTimeInfoCallback() {
    final RealTimeInfo realTimeInfo = new RealTimeInfo();

    Throwable throwable = new Throwable();
    when(mockApiService.getRealTimeInfo(ArgumentMatchers.<String, String>anyMap()))
        .thenReturn(Observable.<RealTimeInfo>error(throwable));

    networkClient.getRealTimeInformation(mockRealTimeInfoCallback, "LUAS11");

    verify(mockRealTimeInfoCallback, never()).onSuccess(realTimeInfo);
    verify(mockRealTimeInfoCallback, times(1)).onError();
  }
}

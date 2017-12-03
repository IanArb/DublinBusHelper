package com.ianarbuckle.dublinbushelper.search.searchlist.stop.ui.core.presenter;

import com.ianarbuckle.dublinbushelper.models.stopinfo.StopInformation;
import com.ianarbuckle.dublinbushelper.search.searchlist.common.BaseSearchPresenter;
import com.ianarbuckle.dublinbushelper.search.searchlist.stop.ui.StopSearchListView;
import com.ianarbuckle.dublinbushelper.search.searchlist.stop.ui.core.interactor.StopSearchInteractor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

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
public class BaseSearchPresenterTest {

  private BaseSearchPresenter presenter;

  @Mock
  StopSearchInteractor interactor;

  @Mock
  StopSearchListView view;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);

    presenter = new DefaultStopSearchPresenter(view, interactor);

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
  public void should_fetchAllStopsOnSuccess() throws Exception {
    StopInformation stopInformation = new StopInformation();
    when(interactor.fetchStopInformation()).thenReturn(Observable.just(stopInformation));

    presenter.onCreate();

    verify(view, times(1)).showProgress();
    verify(view, times(1)).hideProgress();
  }

  @Test
  public void should_showErrorOnFailure() throws Exception {
    Throwable throwable = new Throwable();
    when(interactor.fetchStopInformation()).thenReturn(Observable.error(throwable));

    presenter.onCreate();

    verify(view, times(2)).showError();
  }
}

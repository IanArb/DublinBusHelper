package com.ianarbuckle.dublinbushelper;

import com.ianarbuckle.dublinbushelper.helper.LocationHelper;
import com.ianarbuckle.dublinbushelper.transports.schedules.SchedulePresenterImpl;
import com.ianarbuckle.dublinbushelper.transports.schedules.ScheduleView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

/**
 * Created by Ian Arbuckle on 05/04/2017.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SchedulePresenterImplTest {

  @Mock
  SchedulePresenterImpl presenter;

  @Mock
  ScheduleView view;

  @Mock
  LocationHelper locationHelper;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    presenter = new SchedulePresenterImpl(locationHelper, view);
  }

  @Test
  public void testFetchStops() throws Exception {
    presenter.fetchSchedules("LUAS11");
    verify(view).showProgress();
  }

}

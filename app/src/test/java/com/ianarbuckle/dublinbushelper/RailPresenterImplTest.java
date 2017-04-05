package com.ianarbuckle.dublinbushelper;

import com.ianarbuckle.dublinbushelper.firebase.database.DatabaseHelper;
import com.ianarbuckle.dublinbushelper.transports.irishrail.RailPresenterImpl;
import com.ianarbuckle.dublinbushelper.transports.irishrail.RailView;

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
public class RailPresenterImplTest {

  @Mock
  RailPresenterImpl presenter;

  @Mock
  RailView view;

  @Mock
  DatabaseHelper databaseHelper;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    presenter = new RailPresenterImpl(view, databaseHelper);
  }

  @Test
  public void testFetchStations() {
    presenter.fetchStations();
    verify(view).showProgress();
  }
}

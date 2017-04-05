package com.ianarbuckle.dublinbushelper;

import com.ianarbuckle.dublinbushelper.firebase.database.DatabaseHelper;
import com.ianarbuckle.dublinbushelper.helper.LocationHelper;
import com.ianarbuckle.dublinbushelper.helper.LocationHelperImpl;
import com.ianarbuckle.dublinbushelper.transports.dublinbus.DublinBusFragmentView;
import com.ianarbuckle.dublinbushelper.transports.dublinbus.DublinBusPresenterImpl;
import com.ianarbuckle.dublinbushelper.transports.dublinbus.DublinBusView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * Created by Ian Arbuckle on 05/04/2017.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class DublinBusPresenterImplTest {

  @Mock
  DublinBusPresenterImpl presenter;

  @Mock
  DublinBusView view;

  @Mock
  DublinBusFragmentView fragmentView;

  @Mock
  DatabaseHelper databaseHelper;

  @Mock
  LocationHelper locationHelper;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    presenter = new DublinBusPresenterImpl(databaseHelper);
    presenter.setView(view);
    presenter.setFragmentView(fragmentView);
    locationHelper = new LocationHelperImpl(view.getContext());
  }

  @Test
  public void testFetchStop() throws Exception{
    presenter.fetchStops();
    verify(view).showProgress();
  }

  @Test
  public void testSendToFirebaseDatabase() throws Exception {
    presenter.sendToDatabase("10th Feb 2017", "1999", "Marino", "123", 14, 14);
    verify(databaseHelper).sendFavouriteStopToDatabase(anyString(), anyString(), anyString(), anyString(), anyFloat(), anyFloat());
    verify(fragmentView).setSuccessMessage();
  }

  @Test
  public void testSendToFirebaseDatabaseIsNull() throws Exception {
    presenter.sendToDatabase(null, null, null, null, 0, 0);
    verify(fragmentView).setErrorMessage();
  }

  @Test
  public void testSendToFirebaseDatabaseIsEmpty() {
    presenter.sendToDatabase("", "", "", "", 0, 0);
    verify(fragmentView).setErrorMessage();
  }
}

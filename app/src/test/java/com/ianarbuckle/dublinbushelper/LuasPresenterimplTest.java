package com.ianarbuckle.dublinbushelper;

import com.ianarbuckle.dublinbushelper.firebase.database.DatabaseHelper;
import com.ianarbuckle.dublinbushelper.models.stopinfo.Result;
import com.ianarbuckle.dublinbushelper.transports.luas.LuasPresenterImpl;
import com.ianarbuckle.dublinbushelper.transports.luas.LuasView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by Ian Arbuckle on 05/04/2017.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class LuasPresenterimplTest {

  @Mock
  LuasPresenterImpl presenter;

  @Mock
  LuasView view;

  @Mock
  DatabaseHelper databaseHelper;

  @Mock
  Result result;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    presenter = new LuasPresenterImpl(view, databaseHelper);
  }

  @Test
  public void testFetchStop() {
    presenter.fetchStops();
    verify(view).showProgress();
  }

}

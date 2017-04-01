package com.ianarbuckle.dublinbushelper.transports.schedules;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.ianarbuckle.dublinbushelper.BaseActivity;
import com.ianarbuckle.dublinbushelper.BaseFragment;
import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.utils.Constants;
import com.ianarbuckle.dublinbushelper.utils.UiUtils;

/**
 * Created by Ian Arbuckle on 02/03/2017.
 *
 */

public class ScheduleActivity extends BaseActivity {

  public static Intent newIntent(Context context) {
    return new Intent(context, ScheduleActivity.class);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initFragment();
    initToolbar();
  }

  @Override
  protected void initLayout() {
    setContentView(R.layout.activity_container);
  }

  @Override
  protected void initToolbar() {
    super.initToolbar();
    String displayName = getIntent().getStringExtra(Constants.DISPLAYNAME_KEY);
    assert toolbar != null;
    UiUtils.customiseToolbar(toolbar);
    UiUtils.backStyleActionBar(toolbar);
    toolbar.setTitle(displayName);
    assert getSupportActionBar() != null;
    getSupportActionBar().setHomeButtonEnabled(true);
    getSupportActionBar().setHomeAsUpIndicator(UiUtils.backStyleActionBar(toolbar));

  }

  private void initFragment() {
    FragmentManager fragmentManager = getSupportFragmentManager();

    if(fragmentManager.findFragmentByTag(Constants.SCHEDULE_FRAGMENT) != null) {
      return;
    }

    BaseFragment.switchFragment(fragmentManager, ScheduleFragment.newInstance(), Constants.SCHEDULE_FRAGMENT, false);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch(item.getItemId()) {
      case android.R.id.home:
        finish();
    }
    return true;
  }
}

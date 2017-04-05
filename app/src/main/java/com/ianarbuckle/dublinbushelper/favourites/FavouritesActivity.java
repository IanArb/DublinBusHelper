package com.ianarbuckle.dublinbushelper.favourites;

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
 * Created by Ian Arbuckle on 03/04/2017.
 *
 */

public class FavouritesActivity extends BaseActivity {

  public static Intent newIntent(Context context) {
    return new Intent(context, FavouritesActivity.class);
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
    assert toolbar != null;
    UiUtils.customiseToolbar(toolbar);
    UiUtils.backStyleActionBar(toolbar);
    toolbar.setTitle(getString(R.string.tab_title_favourites));
    assert getSupportActionBar() != null;
    getSupportActionBar().setHomeButtonEnabled(true);
    getSupportActionBar().setHomeAsUpIndicator(UiUtils.backStyleActionBar(toolbar));

  }

  private void initFragment() {
    FragmentManager fragmentManager = getSupportFragmentManager();

    if(fragmentManager.findFragmentByTag(Constants.FAVOURITES_FRAGMENT) != null) {
      return;
    }

    BaseFragment.switchFragment(fragmentManager, FavouritesFragment.newInstance(), Constants.FAVOURITES_FRAGMENT, false);
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

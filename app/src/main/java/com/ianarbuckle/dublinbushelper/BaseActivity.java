package com.ianarbuckle.dublinbushelper;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ianarbuckle.dublinbushelper.dublinbus.DublinBusDublinBusFragment;
import com.ianarbuckle.dublinbushelper.utils.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ian Arbuckle on 14/02/2017.
 *
 */

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

  Unbinder unbinder;

  @Nullable
  @BindView(R.id.toolbar)
  Toolbar toolbar;

  @Nullable
  @BindView(R.id.nav_view)
  NavigationView navigationView;

  @Nullable
  @BindView(R.id.drawer_layout)
  DrawerLayout drawerLayout;

  protected ProgressDialog progressDialog;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initLayout();

    ButterKnife.bind(this);

    butterKnifeUnbinder();

    initToolbar();

    initNavView();
  }

  protected abstract void initLayout();

  private void butterKnifeUnbinder() {
    unbinder = ButterKnife.bind(this);
  }

  private void initNavView() {
    if(navigationView != null) {
      navigationView.setNavigationItemSelectedListener(this);

    }
  }

  protected void initToolbar() {
    if (toolbar != null) {
      UiUtils.customiseToolbar(toolbar);
      UiUtils.colourAndStyleActionBar(toolbar);
      setSupportActionBar(toolbar);
      if (getSupportActionBar() != null) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(UiUtils.colourAndStyleActionBar(toolbar));
      }
    }
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    int itemId = item.getItemId();

    switch (itemId) {
      case R.id.nav_home:
        DublinBusDublinBusFragment.newInstance();
        break;
      case R.id.nav_favourites:
        BlankFragment.newInstance();
        break;
      case R.id.nav_signout:
        BlankFragment.newInstance();
        break;
    }

    if(drawerLayout != null) {
      drawerLayout.closeDrawer(GravityCompat.START);
    }

    if(item.isChecked()) {
      item.setCheckable(false);
    } else {
      item.setChecked(true);
    }

    return true;
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    if(drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawers();
    }
  }
}

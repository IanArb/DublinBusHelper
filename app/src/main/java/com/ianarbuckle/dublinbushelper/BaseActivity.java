package com.ianarbuckle.dublinbushelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.ianarbuckle.dublinbushelper.favourites.FavouritesActivity;
import com.ianarbuckle.dublinbushelper.utils.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ian Arbuckle on 20/08/2017.
 *
 */

public abstract class BaseActivity extends AppCompatActivity {

  @Nullable
  @BindView(R.id.toolbar)
  protected Toolbar toolbar;

  @Nullable
  @BindView(R.id.navigation)
  BottomNavigationView bottomNavigationView;

  private Unbinder unbinder;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initLayout();
    ButterKnife.bind(this);
    butterKnifeUnbinder();
    initToolbar(false);
    initNavBottomBar();
  }

  protected abstract void initLayout();

  protected void initToolbar(boolean isHomeAsUpEnabled) {
    UiUtils.customiseToolbar(toolbar);
    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null && toolbar != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(isHomeAsUpEnabled);
      getSupportActionBar().setHomeAsUpIndicator(UiUtils.colourAndStyleActionBar(toolbar));
      toolbar.setTitle(R.string.app_name);
    }
  }

  private void butterKnifeUnbinder() {
    unbinder = ButterKnife.bind(this);
  }

  private void initNavBottomBar() {
    if (bottomNavigationView != null) {
      bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationOnClickListener());
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
  }

  private class BottomNavigationOnClickListener implements BottomNavigationView.OnNavigationItemSelectedListener {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()) {
        case R.id.nav_favourites:
          Intent intent = FavouritesActivity.Companion.newIntent(getApplicationContext());
          startActivity(intent);
        case R.id.nav_nearby:
          Toast.makeText(getApplicationContext(), "Feature coming soon", Toast.LENGTH_SHORT).show();
        case R.id.nav_profile:
          Toast.makeText(getApplicationContext(), "Feature coming soon", Toast.LENGTH_SHORT).show();
      }
      return true;
    }
  }
}

package com.ianarbuckle.dublinbushelper.transports;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.ianarbuckle.dublinbushelper.BaseActivity;
import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.transports.dublinbus.DublinBusFragment;
import com.ianarbuckle.dublinbushelper.transports.irishrail.RailFragment;
import com.ianarbuckle.dublinbushelper.transports.luas.LuasFragment;

import butterknife.BindView;

/**
 * Created by Ian Arbuckle on 14/02/2017.
 *
 */

public class TransportsPagerActivity extends BaseActivity {

  @BindView(R.id.viewPager)
  ViewPager viewPager;

  @Nullable
  @BindView(R.id.tabs)
  TabLayout tabLayout;

  public static Intent newIntent(Context context) {
    return new Intent(context, TransportsPagerActivity.class);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initTabLayout();

    initPager();
  }

  @Override
  protected void initLayout() {
    setContentView(R.layout.activity_nav_drawer);
  }

  private void initTabLayout() {
    assert tabLayout != null;
    tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_title_dublin_bus));
    tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_title_luas));
    tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_title_irish_rail)));
  }

  private void initPager() {
    assert tabLayout != null;
    final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
    viewPager.setAdapter(adapter);
    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {
      }
    });
  }

  private class PagerAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;

    public PagerAdapter(FragmentManager fragmentManager, int numOfTabs) {
      super(fragmentManager);
      this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
      switch (position) {
        case 0 :
          return DublinBusFragment.newInstance();
        case 1 :
          return LuasFragment.newInstance();
        case 2 :
          return RailFragment.newInstance();
        default:
          return null;
      }
    }

    @Override
    public int getCount() {
      return numOfTabs;
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch(item.getItemId()) {
      case android.R.id.home:
        if(drawerLayout != null) {
          drawerLayout.openDrawer(GravityCompat.START);
        }
    }
    return true;
  }
}

package com.ianarbuckle.dublinbushelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.ianarbuckle.dublinbushelper.map.BusMapFragment;

import butterknife.BindView;

/**
 * Created by Ian Arbuckle on 14/02/2017.
 *
 */

public class PagerActivity extends BaseActivity {

  @BindView(R.id.viewPager)
  ViewPager viewPager;

  @Nullable
  @BindView(R.id.tabs)
  TabLayout tabLayout;

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
    tabLayout.addTab(tabLayout.newTab().setText("Map"));
    tabLayout.addTab(tabLayout.newTab().setText("Favourites"));
    tabLayout.addTab(tabLayout.newTab().setText("Updates"));
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
          return BusMapFragment.newInstance();
        case 1 :
          return BlankFragment.newInstance();
        case 2 :
          return BlankFragment.newInstance();
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

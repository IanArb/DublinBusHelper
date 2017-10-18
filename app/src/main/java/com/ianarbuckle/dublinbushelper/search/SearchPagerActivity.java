package com.ianarbuckle.dublinbushelper.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.ianarbuckle.dublinbushelper.BaseActivity;
import com.ianarbuckle.dublinbushelper.BlankFragment;
import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.search.dublinbus.route.SearchRouteFragment;
import com.ianarbuckle.dublinbushelper.search.dublinbus.stop.SearchStopFragment;

import butterknife.BindView;

/**
 * Created by Ian Arbuckle on 15/10/2017.
 *
 */

public class SearchPagerActivity extends BaseActivity {

  @BindView(R.id.tabLayout)
  TabLayout tabLayout;

  @BindView(R.id.viewPager)
  ViewPager viewPager;

  public static void init(Activity activity) {
    Intent intent = new Intent(activity, SearchPagerActivity.class);
    activity.startActivity(intent);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initToolbar(false);
    initTabLayout();
    initPager();
  }

  @Override
  protected void initLayout() {
    setContentView(R.layout.activity_main_container);
  }

  @Override
  protected void initToolbar(boolean isHomeAsUpEnabled) {
    super.initToolbar(isHomeAsUpEnabled);
    if(toolbar != null) {
      toolbar.setTitle("Dublin Bus");
    }
  }

  private void initTabLayout() {
    tabLayout.addTab(tabLayout.newTab().setText("Route"));
    tabLayout.addTab(tabLayout.newTab().setText("RouteStop"));
    tabLayout.addTab(tabLayout.newTab().setText("News"));
  }

  private void initPager() {
    SearchPagerAdapter adapter = new SearchPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    for (Fragment fragment : getSupportFragmentManager().getFragments()) {
      fragment.onActivityResult(requestCode, resultCode, data);
    }
  }

  private class SearchPagerAdapter extends FragmentStatePagerAdapter {
    private final int numOfTabs;

    public SearchPagerAdapter(FragmentManager fragmentManager, int numOfTabs) {
      super(fragmentManager);
      this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
      switch (position) {
        case 0:
          return SearchRouteFragment.newInstance();
        case 1:
          return SearchStopFragment.newInstance();
        case 2:
          return BlankFragment.Companion.newInstance();
      }
      return null;
    }

    @Override
    public int getCount() {
      return numOfTabs;
    }
  }
}

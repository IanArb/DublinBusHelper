package com.ianarbuckle.dublinbushelper.authentication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.ianarbuckle.dublinbushelper.BaseActivity;
import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.authentication.login.LoginFragment;
import com.ianarbuckle.dublinbushelper.authentication.register.RegisterFragment;

import butterknife.BindView;

/**
 * Created by Ian Arbuckle on 04/03/2017.
 *
 */

public class AuthPagerActivity extends BaseActivity {

  @BindView(R.id.tabs)
  TabLayout tabLayout;

  @BindView(R.id.viewpager)
  ViewPager viewPager;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initTabLayout();
    initPager();
  }

  @Override
  protected void initLayout() {
    setContentView(R.layout.activity_pager_auth);
  }

  private void initTabLayout() {
    tabLayout.addTab(tabLayout.newTab().setText("Sign In"));
    tabLayout.addTab(tabLayout.newTab().setText("Sign Up"));
  }

  private void initPager() {
    final AuthPagerAdapter adapter = new AuthPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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

  private class AuthPagerAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;

    public AuthPagerAdapter(FragmentManager fragmentManager, int numOfTabs) {
      super(fragmentManager);
      this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
      switch (position) {
        case 0:
          return LoginFragment.newInstance();
        case 1:
          return RegisterFragment.newInstance();
        default:
          return null;
      }
    }

    @Override
    public int getCount() {
      return numOfTabs;
    }
  }

}

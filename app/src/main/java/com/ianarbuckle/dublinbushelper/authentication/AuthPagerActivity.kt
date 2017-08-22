package com.ianarbuckle.dublinbushelper.authentication

import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.ianarbuckle.dublinbushelper.BaseActivity

import com.ianarbuckle.dublinbushelper.R
import com.ianarbuckle.dublinbushelper.authentication.login.LoginFragment
import com.ianarbuckle.dublinbushelper.authentication.register.RegisterFragment
import com.ianarbuckle.dublinbushelper.utils.ToolbarManager
import kotlinx.android.synthetic.main.layout_pager.*
import kotlinx.android.synthetic.main.layout_tab.*
import org.jetbrains.anko.find

/**
 * Created by Ian Arbuckle on 04/03/2017.

 */

class AuthPagerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar(false)
        initTabLayout()
        initPager()
    }

    override fun initToolbar(isHomeAsUpEnabled: Boolean) {
        super.initToolbar(isHomeAsUpEnabled)
        toolbar.title = getString(R.string.app_name)
    }

    override fun initLayout() {
        setContentView(R.layout.activity_pager_auth)
    }

    private fun initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.login))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.register))
    }

    private fun initPager() {
        val adapter = AuthPagerAdapter(supportFragmentManager, tabLayout.tabCount)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private inner class AuthPagerAdapter(fragmentManager: FragmentManager, internal var numOfTabs: Int) : FragmentStatePagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> return LoginFragment.newInstance()
                1 -> return RegisterFragment.newInstance()
                else -> return null
            }
        }

        override fun getCount(): Int {
            return numOfTabs
        }
    }

}

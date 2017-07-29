package com.ianarbuckle.dublinbushelper.transports

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v7.widget.Toolbar
import android.view.MenuItem

import com.ianarbuckle.dublinbushelper.BaseActivity
import com.ianarbuckle.dublinbushelper.R
import com.ianarbuckle.dublinbushelper.transports.dublinbus.DublinBusFragment
import com.ianarbuckle.dublinbushelper.transports.irishrail.RailFragment
import com.ianarbuckle.dublinbushelper.transports.luas.LuasFragment

import kotlinx.android.synthetic.main.layout_pager.*
import kotlinx.android.synthetic.main.layout_tab.*
import org.jetbrains.anko.find

/**
 * Created by Ian Arbuckle on 14/02/2017.

 */

class TransportsPagerActivity : BaseActivity() {

    override val toolbar: Toolbar by lazy {
        find<Toolbar>(R.id.toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar()
        initTabLayout()
        initPager()
    }

    private fun initToolbar() {
        toolbarTitle = "Transport Helper Ireland"
    }

    override fun initLayout() {
        setContentView(R.layout.activity_nav_drawer)
    }

    private fun initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_title_dublin_bus))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_title_luas))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_title_irish_rail)))
    }

    private fun initPager() {
        val adapter = PagerAdapter(supportFragmentManager, tabLayout.tabCount)
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

    private inner class PagerAdapter(fragmentManager: FragmentManager, internal var numOfTabs: Int) : FragmentStatePagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> return DublinBusFragment.newInstance()
                1 -> return LuasFragment.newInstance()
                2 -> return RailFragment.newInstance()
                else -> return null
            }
        }

        override fun getCount(): Int {
            return numOfTabs
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout?.openDrawer(GravityCompat.START)
            }
        }
        return true
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, TransportsPagerActivity::class.java)
        }
    }
}

package com.ianarbuckle.dublinbushelper.transports.schedules

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem

import com.ianarbuckle.dublinbushelper.BaseActivity
import com.ianarbuckle.dublinbushelper.BaseFragment
import com.ianarbuckle.dublinbushelper.R
import com.ianarbuckle.dublinbushelper.utils.Constants
import org.jetbrains.anko.find

/**
 * Created by Ian Arbuckle on 02/03/2017.

 */

class ScheduleActivity : BaseActivity() {

    override val toolbar: Toolbar by lazy {
        find<Toolbar>(R.id.toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFragment()
        initToolbar()
    }

    override fun initLayout() {
        setContentView(R.layout.activity_container)
    }

    private fun initToolbar() {
        val displayName = intent.getStringExtra(Constants.DISPLAYNAME_KEY)
        toolbar.title = displayName
        enableHomeAsUp { onBackPressed() }
    }

    private fun initFragment() {
        val fragmentManager = supportFragmentManager

        if (fragmentManager.findFragmentByTag(Constants.SCHEDULE_FRAGMENT) != null) {
            return
        }

        BaseFragment.switchFragment(fragmentManager, ScheduleFragment.newInstance(), Constants.SCHEDULE_FRAGMENT, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, ScheduleActivity::class.java)
        }
    }
}

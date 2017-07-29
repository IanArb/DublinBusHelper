package com.ianarbuckle.dublinbushelper.favourites

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
 * Created by Ian Arbuckle on 03/04/2017.

 */

class FavouritesActivity : BaseActivity() {

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
        toolbar.title = getString(R.string.tab_title_favourites)
    }

    private fun initFragment() {
        val fragmentManager = supportFragmentManager

        if (fragmentManager.findFragmentByTag(Constants.FAVOURITES_FRAGMENT) != null) {
            return
        }

        BaseFragment.switchFragment(fragmentManager, FavouritesFragment.newInstance(), Constants.FAVOURITES_FRAGMENT, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, FavouritesActivity::class.java)
        }
    }

}

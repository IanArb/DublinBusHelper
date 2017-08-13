package com.ianarbuckle.dublinbushelper

import android.content.Context
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import butterknife.BindView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ianarbuckle.dublinbushelper.favourites.FavouritesActivity
import com.ianarbuckle.dublinbushelper.transports.TransportsPagerActivity
import com.ianarbuckle.dublinbushelper.utils.CircleTransform
import com.ianarbuckle.dublinbushelper.utils.Constants

import butterknife.ButterKnife
import butterknife.Unbinder
import com.ianarbuckle.dublinbushelper.utils.ToolbarManager
import com.ianarbuckle.dublinbushelper.utils.UIManager

import kotlinx.android.synthetic.main.layout_nav_header_main.*
import kotlinx.android.synthetic.main.layout_navview.*

/**
 * Created by Ian Arbuckle on 14/02/2017.

 */

abstract class BaseActivity : AppCompatActivity(), ToolbarManager, NavigationView.OnNavigationItemSelectedListener {

    internal lateinit var unbinder: Unbinder

    @BindView(R.id.drawerLayout)
    var drawerLayout: DrawerLayout? = null

    val uiManager: UIManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()

        ButterKnife.bind(this)

        butterKnifeUnbinder()

        initNavView()

    }

    protected open fun initNavView() {
        navigationView?.setNavigationItemSelectedListener(this)

        val sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
        val name = sharedPreferences.getString(Constants.NAME_KEY, Constants.DEFAULT_KEY)
        val email = sharedPreferences.getString(Constants.EMAIL_KEY, Constants.DEFAULT_KEY)
        val photo = sharedPreferences.getString(Constants.PHOTO_KEY, Constants.DEFAULT_KEY)

        tvDisplayName?.text = name
        tvEmail?.text = email

//        Glide.with(applicationContext).load(photo)
//                .crossFade()
//                .thumbnail(0.5f)
//                .bitmapTransform(CircleTransform(applicationContext))
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(ivProfile)

        uiManager?.loadImage(photo, ivProfile)
        uiManager?.loadImage(Constants.HEADER_URL, ivHeader)

//        Glide.with(applicationContext).load(Constants.HEADER_URL)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(ivHeader)
    }

    protected abstract fun initLayout()

    private fun butterKnifeUnbinder() {
        unbinder = ButterKnife.bind(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId

        when (itemId) {
            R.id.nav_home -> startActivity(TransportsPagerActivity.newIntent(applicationContext))
            R.id.nav_favourites -> startActivity(FavouritesActivity.newIntent(applicationContext))
            R.id.nav_signout -> BlankFragment.newInstance()
        }

        drawerLayout?.closeDrawer(GravityCompat.START)

        if (item.isChecked) {
            item.isCheckable = false
        } else {
            item.isChecked = true
        }

        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder.unbind()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        drawerLayout?.closeDrawers()
    }
}

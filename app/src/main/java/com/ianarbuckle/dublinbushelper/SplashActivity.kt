package com.ianarbuckle.dublinbushelper

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.ianarbuckle.dublinbushelper.authentication.AuthPagerActivity

/**
 * Created by Ian Arbuckle on 10/10/2016.

 */

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, AuthPagerActivity::class.java)
        startActivity(intent)
        finish()
    }
}

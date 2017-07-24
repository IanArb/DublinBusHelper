package com.ianarbuckle.dublinbushelper

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Ian Arbuckle on 14/02/2017.

 */

class BlankFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_blank, container, false)
    }

    override fun initPresenter() {

    }

    companion object {

        fun newInstance(): Fragment {
            return BlankFragment()
        }
    }
}

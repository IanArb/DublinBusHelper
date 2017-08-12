package com.ianarbuckle.dublinbushelper.utils

import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View

/**
 * Created by Ian Arbuckle on 26/07/2017.
 *
 */
interface ToolbarManager {
    val toolbar: Toolbar

    var toolbarTitle: String

    get() = toolbar.title.toString()
    set(value) {
        toolbar.title = value
    }

    fun enableHomeAsUp(up: () -> Unit) {
        toolbar.navigationIcon = createUpDrawable()
        toolbar.setNavigationOnClickListener {
            up()
        }
    }

    private fun createUpDrawable() =
            with(DrawerArrowDrawable(toolbar.context)) {
                progress = 1f
                this
            }

    fun attachToScroll(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object :
        RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if(dy > 0) {
                    toolbar.slideExit()
                } else {
                    toolbar.slideEnter()
                }
            }
        })
    }

    fun View.slideExit() {
        if(translationY == 0f) {
            animate().translationY(-height.toFloat())
        }
    }

    fun View.slideEnter() {
        if(translationY < 0f) {
            animate().translationY(0f)
        }
    }

}
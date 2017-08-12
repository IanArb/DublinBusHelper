package com.ianarbuckle.dublinbushelper.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * Created by Ian Arbuckle on 06/08/2017.
 *
 */
interface UIManager {

    fun loadImage(url: String, imageView: ImageView) {
        val context: Context = imageView.context
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .thumbnail(0.5f)
                .centerCrop()
                .into(imageView)
    }

}
package com.ianarbuckle.dublinbushelper.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ianarbuckle.dublinbushelper.R;

/**
 * Created by Ian Arbuckle on 20/08/2017.
 *
 */

public class UiUtils {

  public static void customiseToolbar(View view) {
    Toolbar toolbar = (Toolbar) view;
    for(int i = 0; i < toolbar.getChildCount(); i++) {
      View childView = toolbar.getChildAt(i);

      if(childView instanceof TextView) {
        TextView tvToolbar = (TextView) childView;
        tvToolbar.setTextColor(ContextCompat.getColor(childView.getContext(), R.color.colorPrimary));
      }
    }
  }

  public static Drawable colourAndStyleActionBar(View view) {
    final Drawable backArrow;
    backArrow = ContextCompat.getDrawable(view.getContext(), R.drawable.abc_ic_ab_back_material);
    backArrow.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
    return backArrow;
  }

  public static void loadImage(String url, ImageView imageView) {
    Context context = imageView.getContext();
    Drawable drawable = ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_person);
    Glide.with(context)
        .load(url)
        .placeholder(drawable)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .crossFade()
        .thumbnail(0.5f)
        .centerCrop()
        .into(imageView);
  }

  public static void slideEnterTil(TextInputLayout view) {
    if(view.getTranslationY() < 0f) {
      ViewPropertyAnimator animate = view.animate();
      animate.translationY(0f);
      view.setVisibility(View.VISIBLE);
    }
  }

  public static void slideExitTil(TextInputLayout view) {
    if(view.getTranslationY() == 0f) {
      int height = view.getHeight();
      ViewPropertyAnimator animate = view.animate();
      animate.translationY(-height);
      view.setVisibility(View.GONE);
    }
  }
}

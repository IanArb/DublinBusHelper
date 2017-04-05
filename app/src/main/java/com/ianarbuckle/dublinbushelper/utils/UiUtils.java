package com.ianarbuckle.dublinbushelper.utils;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ianarbuckle.dublinbushelper.R;

/**
 * Created by Ian Arbuckle on 19/02/2017.
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
    backArrow = ContextCompat.getDrawable(view.getContext(), R.drawable.ic_menu);
    return backArrow;
  }

  public static Drawable backStyleActionBar(View view) {
    final Drawable backArrow;
    backArrow = ContextCompat.getDrawable(view.getContext(), R.drawable.ic_arrow_back);
    backArrow.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
    return backArrow;
  }
}

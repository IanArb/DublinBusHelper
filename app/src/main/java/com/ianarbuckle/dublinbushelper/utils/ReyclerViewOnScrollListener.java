package com.ianarbuckle.dublinbushelper.utils;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Ian Arbuckle on 27/08/2017.
 *
 */

public class ReyclerViewOnScrollListener extends RecyclerView.OnScrollListener {

  TextInputLayout tilFilter;

  public ReyclerViewOnScrollListener(TextInputLayout tilFilter) {
    this.tilFilter = tilFilter;
  }

  @Override
  public void onScrolled(RecyclerView recyclerView, int dimenx, int dimeny) {
    super.onScrolled(recyclerView, dimenx, dimeny);
    if (dimeny > 0) {
      UiUtils.slideExitTil(tilFilter);
    } else {
      UiUtils.slideEnterTil(tilFilter);
    }
  }
}

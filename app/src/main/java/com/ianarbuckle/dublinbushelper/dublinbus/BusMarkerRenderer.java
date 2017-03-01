package com.ianarbuckle.dublinbushelper.dublinbus;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.models.MarkerItemModel;

/**
 * Created by Ian Arbuckle on 20/02/2017.
 *
 */

public class BusMarkerRenderer extends DefaultClusterRenderer<MarkerItemModel> {

  private Context mContext;

  private final IconGenerator iconGenerator;

  public BusMarkerRenderer(Context context, GoogleMap map, ClusterManager<MarkerItemModel> clusterManager) {
    super(context, map, clusterManager);

    mContext = context;
    iconGenerator = new IconGenerator(context.getApplicationContext());
  }

  @Override
  protected void onBeforeClusterItemRendered(MarkerItemModel item, MarkerOptions markerOptions) {
    iconGenerator.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_directions_bus));

    final Bitmap bitmap = iconGenerator.makeIcon();
    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
  }

  @Override
  protected boolean shouldRenderAsCluster(Cluster<MarkerItemModel> cluster) {
    return cluster.getSize() > 1;
  }

  @Override
  protected int getBucket(Cluster<MarkerItemModel> cluster) {
    return cluster.getSize();
  }
}

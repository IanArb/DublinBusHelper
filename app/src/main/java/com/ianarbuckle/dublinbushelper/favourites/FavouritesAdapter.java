package com.ianarbuckle.dublinbushelper.favourites;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.models.Favourites;
import com.ianarbuckle.dublinbushelper.transports.schedules.ScheduleActivity;
import com.ianarbuckle.dublinbushelper.utils.Constants;

/**
 * Created by Ian Arbuckle on 03/04/2017.
 *
 */

public class FavouritesAdapter extends FirebaseRecyclerAdapter<Favourites, FavouritesViewHolder> {

  private Context context;

  public FavouritesAdapter(Class<Favourites> modelClass, int modelLayout, Class<FavouritesViewHolder> viewHolderClass, DatabaseReference ref, Context context) {
    super(modelClass, modelLayout, viewHolderClass, ref);
    this.context = context;
  }

  @Override
  protected void populateViewHolder(FavouritesViewHolder viewHolder, final Favourites model, final int position) {
    TextView tvName = viewHolder.tvName;
    TextView tvRoute = viewHolder.tvRoute;
    TextView tvStopId = viewHolder.tvStopId;
    TextView tvTime = viewHolder.tvTime;
    ImageButton ibRemove = viewHolder.ibRemove;

    tvName.setText(model.getName());
    tvStopId.setText(model.getStopId());
    tvTime.setText(model.getTime());
    setRoutesText(model, tvRoute);

    setScheduleOnClick(viewHolder, model);

    ibRemove.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        getRef(position).removeValue();
        notifyDataSetChanged();
      }
    });
  }

  private void setScheduleOnClick(FavouritesViewHolder viewHolder, final Favourites model) {
    viewHolder.btnSchedule.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String stopId = model.getStopId();
        String displayName = model.getName();
        float latitude = model.getLatitude();
        float longtitude = model.getLongitude();

        Intent intent = ScheduleActivity.newIntent(context);
        intent.putExtra(Constants.STOPID_KEY, stopId);
        intent.putExtra(Constants.LAT_KEY, latitude);
        intent.putExtra(Constants.LONG_KEY, longtitude);
        intent.putExtra(Constants.DISPLAYNAME_KEY, displayName);
        context.startActivity(intent);
      }
    });
  }

  private void setRoutesText(Favourites model, TextView tvRoute) {
    switch (model.getRoutes()) {
      case Constants.GREEN_LINE:
        tvRoute.setText(context.getString(R.string.luas_green));
        tvRoute.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));
        break;
      case Constants.RED_LINE:
        tvRoute.setText(context.getString(R.string.luas_red));
        tvRoute.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        break;
      case Constants.COMMUTER_ID:
        tvRoute.setText(context.getString(R.string.commuter_label));
        break;
      case Constants.INTERCITY_ID:
        tvRoute.setText(context.getString(R.string.intercity_label));
        break;
      case Constants.COMMUTER_DART_ID:
        tvRoute.setText(context.getString(R.string.dart_commuter_label));
        break;
      case Constants.DART_COMMUTER_ID:
        tvRoute.setText(context.getString(R.string.dart_commuter_label));
        break;
      case Constants.COMMUTER_INTER_DART_ID:
        tvRoute.setText(context.getString(R.string.intercity_dart_commuter_label));
        break;
      case Constants.COMMUTER_INTER_ID:
        tvRoute.setText(context.getString(R.string.commuter_intercity_label));
        break;
      case Constants.INTER_COMMUTER_ID:
        tvRoute.setText(context.getString(R.string.commuter_intercity_label));
        break;
      default:
        tvRoute.setText(model.getRoutes());
    }
  }
}

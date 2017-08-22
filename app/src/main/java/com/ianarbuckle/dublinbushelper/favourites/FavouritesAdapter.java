package com.ianarbuckle.dublinbushelper.favourites;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.ianarbuckle.dublinbushelper.models.Favourites;

/**
 * Created by Ian Arbuckle on 03/04/2017.
 *
 */

public class FavouritesAdapter extends FirebaseRecyclerAdapter<Favourites, FavouritesViewHolder> {

  private FavouritesPresenterImpl presenter;

  public FavouritesAdapter(Class<Favourites> modelClass, int modelLayout, Class<FavouritesViewHolder> viewHolderClass, DatabaseReference ref, FavouritesPresenterImpl presenter) {
    super(modelClass, modelLayout, viewHolderClass, ref);
    this.presenter = presenter;
  }

  @Override
  protected void populateViewHolder(final FavouritesViewHolder viewHolder, final Favourites model, final int position) {
    if(model != null) {
      presenter.onBindRowViewAtPosition(model, position, viewHolder);
      presenter.setRouteText(model, position, viewHolder);
      setClickListeners(viewHolder, model, position);
    }
  }

  private void setClickListeners(final FavouritesViewHolder viewHolder, final Favourites model, final int position) {
    Button btnSchedule = viewHolder.btnSchedule;

    btnSchedule.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        presenter.onRowClickPosition(model, viewHolder.getAdapterPosition(), viewHolder);
      }
    });

    ImageButton ibRemove = viewHolder.ibRemove;

    ibRemove.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        getRef(position).removeValue();
        notifyDataSetChanged();
      }
    });
  }

}

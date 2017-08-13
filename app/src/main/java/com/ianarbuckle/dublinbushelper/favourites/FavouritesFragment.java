package com.ianarbuckle.dublinbushelper.favourites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ianarbuckle.dublinbushelper.BaseFragment;
import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.TransportHelperApplication;
import com.ianarbuckle.dublinbushelper.favourites.di.DaggerFavouritesComponent;
import com.ianarbuckle.dublinbushelper.favourites.di.FavouritesModule;
import com.ianarbuckle.dublinbushelper.models.Favourites;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Ian Arbuckle on 03/04/2017.
 *
 */

public class FavouritesFragment extends BaseFragment implements FavouritesView {

  @BindView(R.id.rv)
  RecyclerView recyclerView;

  @BindView(R.id.tvEmptyMessage)
  TextView tvEmptyMessage;

  LinearLayoutManager linearLayoutManager;

  FirebaseRecyclerAdapter<Favourites, FavouritesViewHolder> adapter;

  DatabaseReference databaseReference;
  DatabaseReference childRef;

  @Inject
  FavouritesPresenterImpl presenter;

  public static Fragment newInstance() {
    return new FavouritesFragment();
  }

  @Override
  protected void injectDagger() {
    DaggerFavouritesComponent.builder()
        .applicationComponent(TransportHelperApplication.getApplicationComponent(getContext()))
        .favouritesModule(new FavouritesModule(this))
        .build()
        .inject(this);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_favourites, container, false);
  }

  @Override
  public void onStart() {
    super.onStart();
    attachRecyclerView();
  }

  private void attachRecyclerView() {
    linearLayoutManager = new LinearLayoutManager(getContext());
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(linearLayoutManager);
    databaseReference = FirebaseDatabase.getInstance().getReference();
    childRef = databaseReference.child("favourites");
    adapter = new FavouritesAdapter(Favourites.class, R.layout.layout_card_favourites, FavouritesViewHolder.class, childRef, presenter);
    recyclerView.setAdapter(adapter);
  }

}

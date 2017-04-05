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
import com.ianarbuckle.dublinbushelper.models.Favourites;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 03/04/2017.
 *
 */

public class FavouritesFragment extends BaseFragment {

  @BindView(R.id.rv)
  RecyclerView recyclerView;

  @BindView(R.id.tvEmptyMessage)
  TextView tvEmptyMessage;

  LinearLayoutManager linearLayoutManager;

  FirebaseRecyclerAdapter<Favourites, FavouritesViewHolder> adapter;

  DatabaseReference databaseReference;
  DatabaseReference childRef;

  public static Fragment newInstance() {
    return new FavouritesFragment();
  }

  @Override
  protected void initPresenter() {
    //Stub method
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_favourites, container, false);
    ButterKnife.bind(this, view);
    attachRecyclerView();
    return view;
  }

  private void attachRecyclerView() {
    linearLayoutManager = new LinearLayoutManager(getContext());
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(linearLayoutManager);
    databaseReference = FirebaseDatabase.getInstance().getReference();
    childRef = databaseReference.child("favourites");
    adapter = new FavouritesAdapter(Favourites.class, R.layout.layout_card_favourites, FavouritesViewHolder.class, childRef, getContext());
    recyclerView.setAdapter(adapter);
  }

}

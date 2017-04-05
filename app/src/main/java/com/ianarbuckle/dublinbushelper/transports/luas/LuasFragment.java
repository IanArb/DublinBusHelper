package com.ianarbuckle.dublinbushelper.transports.luas;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ianarbuckle.dublinbushelper.BaseFragment;
import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.TransportHelperApplication;
import com.ianarbuckle.dublinbushelper.firebase.database.DatabaseHelper;
import com.ianarbuckle.dublinbushelper.models.stopinfo.Result;
import com.ianarbuckle.dublinbushelper.utils.Constants;
import com.ianarbuckle.dublinbushelper.utils.ErrorDialogFragment;
import com.ianarbuckle.dublinbushelper.utils.OnRecyclerItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 02/03/2017.
 *
 */

public class LuasFragment extends BaseFragment implements LuasView {

  LinearLayoutManager linearLayoutManager;

  @BindView(R.id.rv)
  RecyclerView recyclerView;

  @BindView(R.id.tilFilter)
  TextInputLayout tilFilter;

  @BindView(R.id.rlProgress)
  RelativeLayout rlProgress;

  @BindView(R.id.progressBar)
  ProgressBar progressBar;

  private LuasPresenterImpl presenter;

  public static Fragment newInstance() {
    return new LuasFragment();
  }

  @Override
  public void onStart() {
    super.onStart();
    presenter.fetchStops();
  }

  @Override
  protected void initPresenter() {
    DatabaseHelper databaseHelper = TransportHelperApplication.getAppInstance().getDatabaseHelper();
    presenter = new LuasPresenterImpl(this, databaseHelper);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_luas, container, false);
    ButterKnife.bind(this, view);
    recyclerView.setHasFixedSize(true);
    linearLayoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(linearLayoutManager);
    return view;
  }

  @Override
  public void setAdapter(final LuasAdapter luasAdapter) {
    recyclerView.setAdapter(luasAdapter);
    onClickListener(luasAdapter);
    filterListener(luasAdapter);
  }

  private void filterListener(final LuasAdapter luasAdapter) {
    final EditText editText = tilFilter.getEditText();
    assert editText != null;
    editText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence string, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence string, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable string) {
        luasAdapter.getFilter().filter(setFilter());
      }
    });
  }

  private void onClickListener(LuasAdapter luasAdapter) {
    luasAdapter.setOnRecyclerItemLongClickListener(new OnRecyclerItemClickListener() {
      @Override
      public void onItemClick(RecyclerView.Adapter adapter, Result result, int position) {
        presenter.sendToDatabase(result);
      }
    });
  }



  @Override
  public void showProgress() {
    if(progressBar != null) {
      progressBar.setProgress(100);
    }
  }

  @Override
  public void hideProgress() {
    if(progressBar != null) {
      rlProgress.setVisibility(View.GONE);
      progressBar.setVisibility(View.GONE);
    }
  }

  @Override
  public void showErrorMessage() {
    FragmentTransaction fragmentTransaction = getFragmentTransaction();
    DialogFragment dialogFragment = ErrorDialogFragment.newInstance(R.string.error_dialog_title);
    dialogFragment.show(fragmentTransaction, Constants.DIALOG_FRAGMENT);
  }

  @NonNull
  private FragmentTransaction getFragmentTransaction() {
    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
    Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(Constants.DIALOG_FRAGMENT);

    if (fragment != null) {
      fragmentTransaction.remove(fragment);
    }

    fragmentTransaction.addToBackStack(null);
    return fragmentTransaction;
  }

  @Override
  public void showSuccessMessage() {
    Toast.makeText(getContext(), "Saved to favourites", Toast.LENGTH_SHORT).show();
  }

  @Override
  public String setFilter() {
    EditText editText = tilFilter.getEditText();
    assert editText != null;
    return editText.getText().toString();
  }
}

package com.ianarbuckle.dublinbushelper.transports.irishrail;

import android.os.Bundle;
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
import com.ianarbuckle.dublinbushelper.models.stopinfo.Result;
import com.ianarbuckle.dublinbushelper.transports.irishrail.di.DaggerRailComponent;
import com.ianarbuckle.dublinbushelper.utils.Constants;
import com.ianarbuckle.dublinbushelper.utils.ErrorDialogFragment;
import com.ianarbuckle.dublinbushelper.utils.OnRecyclerItemClickListener;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 29/03/2017.
 *
 */

public class RailFragment extends BaseFragment implements RailView {

  @BindView(R.id.rv)
  RecyclerView recyclerView;

  @BindView(R.id.tilFilter)
  TextInputLayout tilFilter;

  @BindView(R.id.rlProgress)
  RelativeLayout rlProgress;

  @BindView(R.id.progressBar)
  ProgressBar progressBar;

  @Inject
  RailPresenterImpl presenter;

  RailAdapter railAdapter;

  LinearLayoutManager linearLayoutManager;

  public static Fragment newInstance() {
    return new RailFragment();
  }

  @Override
  protected void injectDagger() {
    DaggerRailComponent.builder()
        .applicationComponent(TransportHelperApplication.getApplicationComponent(getContext()))
        .railModule(new com.ianarbuckle.dublinbushelper.transports.irishrail.di.RailModule(this))
        .build()
        .inject(this);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_rail, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onStart() {
    super.onStart();
    presenter.fetchStations();
    attachRecyclerView();
  }

  private void attachRecyclerView() {
    recyclerView.setHasFixedSize(true);
    linearLayoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(linearLayoutManager);
    railAdapter = new RailAdapter(presenter);
    recyclerView.setAdapter(railAdapter);
    filterListener(railAdapter);
    onClickListener(railAdapter);
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

  @Nonnull
  private FragmentTransaction getFragmentTransaction() {
    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
    Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(Constants.DIALOG_FRAGMENT);

    if (fragment != null) {
      fragmentTransaction.remove(fragment);
    }

    fragmentTransaction.addToBackStack(null);
    return fragmentTransaction;
  }

  private void filterListener(final RailAdapter railAdapter) {
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
        railAdapter.getFilter().filter(setFilter());
        railAdapter.updateList(presenter.getResultsList());
      }
    });
  }

  private void onClickListener(RailAdapter adapter) {
    adapter.setOnRecyclerItemLongClickListener(new OnRecyclerItemClickListener() {
      @Override
      public void onItemClick(RecyclerView.Adapter adapter, Result result, int position) {
        presenter.sendToDatabase(result);
      }
    });
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

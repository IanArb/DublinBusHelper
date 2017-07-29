package com.ianarbuckle.dublinbushelper.authentication.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.ianarbuckle.dublinbushelper.BaseFragment;
import com.ianarbuckle.dublinbushelper.transports.TransportsPagerActivity;
import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.TransportHelperApplication;
import com.ianarbuckle.dublinbushelper.utils.Constants;
import com.ianarbuckle.dublinbushelper.utils.ErrorDialogFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ian Arbuckle on 04/03/2017.
 *
 */

public class LoginFragment extends BaseFragment implements LoginView, GoogleApiClient.OnConnectionFailedListener {

  @BindView(R.id.tilEmail)
  TextInputLayout tilEmail;

  @BindView(R.id.tilPassword)
  TextInputLayout tilPassword;

  LoginPresenterImpl presenter;

  private GoogleApiClient googleApiClient;

  public static Fragment newInstance() {
    return new LoginFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_login, container, false);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initGoogleSignIn();
  }

  @Override
  protected void initPresenter() {
    presenter = new LoginPresenterImpl(TransportHelperApplication.getAppInstance().getFirebaseAuthHelper());
    presenter.setView(this);
  }

  protected synchronized void initGoogleSignIn() {
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build();

    googleApiClient = new GoogleApiClient.Builder(getActivity())
        .enableAutoManage(getActivity(), this)
        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
        .build();
  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    showFailureDialog(R.string.common_google_failure);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    signInIntent(requestCode, data);
  }

  private void signInIntent(int requestCode, Intent data) {
    if(requestCode == Constants.RC_SIGN_IN) {
      GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
      if(result.isSuccess()) {
        GoogleSignInAccount account = result.getSignInAccount();
        presenter.signInWithGoogle(account);
      } else {
        hideProgressDialog();
        showFailureDialog(R.string.common_google_failure);
      }
    }
  }

  @OnClick(R.id.ibGoogle)
  public void onGoogleSignInClick() {
    showProgressDialog();
    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
    startActivityForResult(signInIntent, Constants.RC_SIGN_IN);
  }

  @OnClick(R.id.btnLogin)
  public void onSignInClick() {
    if(tilEmail.getEditText() != null && tilPassword.getEditText() != null) {
      String email = tilEmail.getEditText().getText().toString();
      String password = tilPassword.getEditText().getText().toString();
      presenter.signInUser(email, password);
    }
  }

  @OnClick(R.id.ibFacebook)
  public void onFacebookSignInClick() {
    Toast.makeText(getContext(), R.string.facebook_placeholder, Toast.LENGTH_SHORT).show();
  }

  @OnClick(R.id.ibTwitter)
  public void onTwitterSignIn() {
    Toast.makeText(getContext(), R.string.twitter_placeholder, Toast.LENGTH_SHORT).show();
  }

  @OnClick(R.id.tvAnnoymous)
  public void onAnnoymousClick() {
    Toast.makeText(getContext(), R.string.annoymous_placeholder, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onSuccess() {
    String userName = presenter.getDisplayName();
    Toast.makeText(getContext(), getString(R.string.welcome_message) + userName + "!", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onFailure() {
    showFailureDialog(R.string.common_message_unsuccess);
  }

  private void showFailureDialog(int errorMessage) {
    FragmentTransaction fragmentTransaction = initFragmentManager();
    DialogFragment dialogFragment = ErrorDialogFragment.newInstance(errorMessage);
    dialogFragment.show(fragmentTransaction, Constants.ERROR_DIALOG_FRAGMENT);
  }

  private FragmentTransaction initFragmentManager() {
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    Fragment fragment = getFragmentManager().findFragmentByTag(Constants.ERROR_DIALOG_FRAGMENT);

    if (fragment != null) {
      fragmentTransaction.remove(fragment);
    }

    fragmentTransaction.addToBackStack(null);
    return fragmentTransaction;
  }

  @Override
  public void showErrorEmailMessage() {
    tilEmail.setErrorEnabled(true);
    tilEmail.setError(getString(R.string.common_email_error_invalid));
  }

  @Override
  public void showErrorPasswordMessage() {
    tilPassword.setErrorEnabled(true);
    tilPassword.setError(getString(R.string.common_invalid_password));
  }

  @Override
  public void onLogin() {
    presenter.setSharedPreferences();
    startActivity(TransportsPagerActivity.Companion.newIntent(getContext()));
  }
}

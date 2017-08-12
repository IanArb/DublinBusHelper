package com.ianarbuckle.dublinbushelper.authentication.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ianarbuckle.dublinbushelper.BaseFragment;
import com.ianarbuckle.dublinbushelper.R;
import com.ianarbuckle.dublinbushelper.TransportHelperApplication;
import com.ianarbuckle.dublinbushelper.authentication.di.AuthModule;
import com.ianarbuckle.dublinbushelper.authentication.di.DaggerAuthComponent;
import com.ianarbuckle.dublinbushelper.authentication.di.RegisterModule;
import com.ianarbuckle.dublinbushelper.utils.Constants;
import com.ianarbuckle.dublinbushelper.utils.ErrorDialogFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ian Arbuckle on 04/03/2017.
 *
 */

public class RegisterFragment extends BaseFragment implements RegisterView {

  @BindView(R.id.tilEmail)
  TextInputLayout tilEmail;

  @BindView(R.id.tilUsername)
  TextInputLayout tilUsername;

  @BindView(R.id.tilPassword)
  TextInputLayout tilPassword;

  @BindView(R.id.tilConfirmPassword)
  TextInputLayout tilConfirmPassword;

  @Inject
  RegisterPresenterImpl presenter;

  public static Fragment newInstance() {
    return new RegisterFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_register, container, false);
  }

  @Override
  protected void injectDagger() {
    DaggerAuthComponent.builder()
        .applicationComponent(TransportHelperApplication.getApplicationComponent(getContext()))
        .authModule(new AuthModule())
        .registerModule(new RegisterModule())
        .build()
        .inject(this);
    presenter.setView(this);
  }

  @OnClick(R.id.btnRegister)
  public void onSignUpClick() {
    if(tilEmail.getEditText() != null && tilUsername.getEditText() != null
        && tilPassword.getEditText() != null && tilConfirmPassword.getEditText() != null) {
      String email = tilEmail.getEditText().getText().toString().trim();
      String username = tilUsername.getEditText().getText().toString().trim();
      String password = tilPassword.getEditText().getText().toString().trim();
      String confirmPassword = tilConfirmPassword.getEditText().getText().toString().trim();
      presenter.validateEmail(email);
      presenter.validateUsername(username);
      presenter.validatePassword(password, confirmPassword);
    }
  }

  @Override
  public void showInvalidEmailMessage() {
    hideProgressDialog();
    tilEmail.setErrorEnabled(true);
    tilEmail.setError(getString(R.string.common_invalid_email));
  }

  @Override
  public void showEmailEmptyMessage() {
    hideProgressDialog();
    tilEmail.setErrorEnabled(true);
    tilEmail.setError(getString(R.string.common_email_error_empty));
  }

  @Override
  public void showPasswordEmptyMessage() {
    hideProgressDialog();
    tilPassword.setErrorEnabled(true);
    tilPassword.setError(getString(R.string.common_error_password_empty_message));
  }

  @Override
  public void showErrorMessage() {
  }

  @Override
  public void signUpOnPasswordsMatch() {

  }

  @Override
  public void showConfirmPasswordEmptyMessage() {
    hideProgressDialog();
    tilConfirmPassword.setErrorEnabled(true);
    tilConfirmPassword.setError(getString(R.string.common_error_confirm_password_empty_message));
  }

  @Override
  public void showUsernameEmptyMessage() {
    hideProgressDialog();
    tilUsername.setErrorEnabled(true);
    tilUsername.setError(getString(R.string.common_error_username_empty_message));
  }

  @Override
  public void showInvalidUsernameMessage() {
    hideProgressDialog();
    tilUsername.setErrorEnabled(true);
    tilUsername.setError(getString(R.string.common_error_username_invalid_message));
  }

  @Override
  public void onSuccess() {
    Toast.makeText(getContext(), "Login success", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onFailure() {
    hideProgressDialog();
    showErrorMessageDialog();
  }

  private void showErrorMessageDialog() {
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    Fragment fragment = getFragmentManager().findFragmentByTag(Constants.ERROR_DIALOG_FRAGMENT);

    if(fragment != null) {
      fragmentTransaction.remove(fragment);
    }

    fragmentTransaction.addToBackStack(null);

    DialogFragment dialogFragment = ErrorDialogFragment.newInstance(R.string.common_message_unsuccess);
    dialogFragment.show(fragmentTransaction, Constants.ERROR_DIALOG_FRAGMENT);
  }
}

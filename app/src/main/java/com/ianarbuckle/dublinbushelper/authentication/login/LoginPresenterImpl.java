package com.ianarbuckle.dublinbushelper.authentication.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.ianarbuckle.dublinbushelper.firebase.RequestListener;
import com.ianarbuckle.dublinbushelper.firebase.authentication.FirebaseAuthHelper;
import com.ianarbuckle.dublinbushelper.utils.Constants;
import com.ianarbuckle.dublinbushelper.utils.StringUtils;

/**
 * Created by Ian Arbuckle on 04/03/2017.
 *
 */

public class LoginPresenterImpl implements LoginPresenter {

  private LoginView view;

  private FirebaseAuthHelper firebaseAuthHelper;

  public LoginPresenterImpl(FirebaseAuthHelper firebaseAuthHelper) {
    this.firebaseAuthHelper = firebaseAuthHelper;
  }

  public void setView(LoginView view) {
    this.view = view;
  }

  @Override
  public void signInWithGoogle(GoogleSignInAccount account) {
    firebaseAuthHelper.googleLogin(account, provideLoginCallback());
  }

  private RequestListener provideLoginCallback() {
    return new RequestListener() {
      @Override
      public void onSucess() {
        view.hideProgressDialog();
        view.onLogin();
        view.onSuccess();
      }

      @Override
      public void onFailure() {
        view.hideProgressDialog();
        view.onFailure();
      }
    };
  }

  @Override
  public void signInUser(String email, String password) {
    if (StringUtils.isStringEmptyorNull(email)) {
      view.showErrorEmailMessage();
      view.hideProgressDialog();
    } else if (StringUtils.isStringEmptyorNull(password)) {
      view.showErrorPasswordMessage();
      view.hideProgressDialog();
    } else {
      view.showProgressDialog();
      firebaseAuthHelper.logOutUser();
      firebaseAuthHelper.signInUser(email, password, provideLoginCallback());
    }
  }

  @Override
  public String getDisplayName() {
    return firebaseAuthHelper.getUserDisplayName();
  }

  @Override
  public void setSharedPreferences() {
    String username = firebaseAuthHelper.getUserDisplayName();
    String email = firebaseAuthHelper.getUserEmail();
    String photoUrl = firebaseAuthHelper.getUserPhoto();
    SharedPreferences sharedPreferences = view.getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(Constants.NAME_KEY, username);
    editor.putString(Constants.EMAIL_KEY, email);
    editor.putString(Constants.PHOTO_KEY, photoUrl);
    editor.apply();
  }
}

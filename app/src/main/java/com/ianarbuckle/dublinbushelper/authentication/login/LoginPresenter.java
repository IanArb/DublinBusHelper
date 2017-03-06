package com.ianarbuckle.dublinbushelper.authentication.login;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by Ian Arbuckle on 04/03/2017.
 *
 */

public interface LoginPresenter {
  void signInWithGoogle(GoogleSignInAccount account);
  void signInUser(String email, String password);
  String getDisplayName();
  void setSharedPreferences();
}

package com.ianarbuckle.dublinbushelper.firebase.authentication;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.ianarbuckle.dublinbushelper.firebase.RequestListener;

/**
 * Created by Ian Arbuckle on 04/03/2017.
 *
 */

public interface FirebaseAuthHelper {
  void googleLogin(GoogleSignInAccount account, RequestListener listener);
  void registerUser(String email, String password, final RequestListener listener);
  void signInUser(String email, String password, final RequestListener listener);
  void logOutUser();
  boolean isUserSignedIn();
  String getUserDisplayName();
  String getUserPhoto();
  String getUserEmail();

}

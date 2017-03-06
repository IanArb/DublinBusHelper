package com.ianarbuckle.dublinbushelper.firebase.authentication;

import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.ianarbuckle.dublinbushelper.firebase.RequestListener;

/**
 * Created by Ian Arbuckle on 04/03/2017.
 *
 */

public class FirebaseAuthHelperImpl implements FirebaseAuthHelper {

  private final FirebaseAuth firebaseAuth;

  public FirebaseAuthHelperImpl(FirebaseAuth firebaseAuth) {
    this.firebaseAuth = firebaseAuth;
  }

  @Override
  public void googleLogin(GoogleSignInAccount account, final RequestListener listener) {
    AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
    firebaseAuth.signInWithCredential(authCredential)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            listener.onSucess();
            if(!task.isSuccessful()) {
              listener.onFailure();
            } else {
              listener.onSucess();
            }
          }
        });
  }

  @Override
  public void registerUser(String email, String password, final RequestListener listener) {
    firebaseAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()) {
              FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
              if(firebaseUser != null) {
                listener.onSucess();
              } else {
                listener.onFailure();
              }
            }
          }
        });
  }

  @Override
  public void signInUser(String email, String password, final RequestListener listener) {
    firebaseAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()) {
              FirebaseUser user = firebaseAuth.getCurrentUser();
              if(user != null) {
                listener.onSucess();
              }
            } else {
              listener.onFailure();
            }
          }
        });
  }

  @Override
  public void logOutUser() {
    if(isUserSignedIn()) {
      firebaseAuth.signOut();
    }
  }

  @Override
  public boolean isUserSignedIn() {
    return firebaseAuth.getCurrentUser() != null;
  }

  @Override
  public String getUserDisplayName() {
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    if(firebaseUser != null) {
      return firebaseUser.getDisplayName();
    }
    return null;
  }

  @Override
  public String getUserPhoto() {
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    if(firebaseUser != null && firebaseUser.getPhotoUrl() != null) {
      return firebaseUser.getPhotoUrl().toString();
    }
    return null;
  }

  @Override
  public String getUserEmail() {
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    if(firebaseUser != null) {
      return firebaseUser.getEmail();
    }
    return null;
  }
}

package com.ianarbuckle.dublinbushelper;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.ianarbuckle.dublinbushelper.authentication.login.LoginPresenterImpl;
import com.ianarbuckle.dublinbushelper.authentication.login.LoginView;
import com.ianarbuckle.dublinbushelper.firebase.RequestListener;
import com.ianarbuckle.dublinbushelper.firebase.authentication.FirebaseAuthHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by Ian Arbuckle on 05/03/2017.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterImplTest {

  private LoginPresenterImpl presenter;

  @Mock
  FirebaseAuthHelper firebaseAuthHelper;

  @Mock
  LoginView view;

  @Mock
  GoogleSignInAccount account;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    presenter = new LoginPresenterImpl(firebaseAuthHelper);
    presenter.setView(view);
  }

  @Test
  public void testGoogleSignIn() {
    presenter.signInWithGoogle(account);
    verify(firebaseAuthHelper).googleLogin(any(GoogleSignInAccount.class), any(RequestListener.class));
  }

  @Test
  public void testLoginUserIfEmailEmpty() throws Exception {
    presenter.signInUser(" ", "password");
    verify(view).showErrorEmailMessage();
    verify(view).hideProgressDialog();
  }

  @Test
  public void testLoginUserIfPasswordEmpty() throws Exception {
    presenter.signInUser("email", " ");
    verify(view).showErrorPasswordMessage();
    verify(view).hideProgressDialog();
  }

  @Test
  public void testLoginUser() throws Exception {
    presenter.signInUser("email", "password");
    verify(view).showProgressDialog();
    verify(firebaseAuthHelper).logOutUser();
    verify(firebaseAuthHelper).signInUser(anyString(), anyString(), any(RequestListener.class));
    verifyNoMoreInteractions(view, firebaseAuthHelper);
  }

}

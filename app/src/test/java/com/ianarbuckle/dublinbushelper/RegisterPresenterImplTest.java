package com.ianarbuckle.dublinbushelper;

import com.ianarbuckle.dublinbushelper.authentication.register.RegisterPresenterImpl;
import com.ianarbuckle.dublinbushelper.authentication.register.RegisterView;
import com.ianarbuckle.dublinbushelper.firebase.RequestListener;
import com.ianarbuckle.dublinbushelper.firebase.authentication.FirebaseAuthHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * Created by Ian Arbuckle on 05/03/2017.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RegisterPresenterImplTest {

  private RegisterPresenterImpl presenter;

  @Mock
  FirebaseAuthHelper firebaseAuthHelper;

  @Mock
  RegisterView view;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    presenter = new RegisterPresenterImpl(firebaseAuthHelper);
    presenter.setView(view);
  }

  @Test
  public void testRegisterValidUser() throws Exception {
    presenter.signUpAccount("email", "password");
    verify(firebaseAuthHelper).logOutUser();
    verify(firebaseAuthHelper).registerUser(anyString(), anyString(), any(RequestListener.class));
  }

  @Test
  public void testAttemptToRegisterIfUserEmailIsEmpty() throws Exception {
    presenter.signUpAccount("  ", "test");
    verify(view).hideProgressDialog();
    verify(view).showInvalidEmailMessage();
  }

  @Test
  public void testIfPasswordsMatchAreEmpty() throws Exception {
    presenter.validatePassword(" ", " ");
    verify(view).hideProgressDialog();
    verify(view).showPasswordEmptyMessage();
  }

  @Test
  public void testIfPasswordsMatchAreDifferent() throws Exception {
    presenter.validatePassword("bacon", "tuna");
    verify(view).showErrorMessage();
    verify(view).hideProgressDialog();
  }

  @Test
  public void testIfPasswordsMatch() throws Exception {
    presenter.validatePassword("bacon", "bacon");
    verify(view).showProgressDialog();
    verify(view).signUpOnPasswordsMatch();
  }

}

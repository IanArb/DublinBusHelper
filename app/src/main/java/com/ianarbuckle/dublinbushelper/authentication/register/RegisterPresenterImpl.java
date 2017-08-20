package com.ianarbuckle.dublinbushelper.authentication.register;

import com.ianarbuckle.dublinbushelper.firebase.RequestListener;
import com.ianarbuckle.dublinbushelper.firebase.authentication.FirebaseAuthHelper;
import com.ianarbuckle.dublinbushelper.utils.Constants;
import com.ianarbuckle.dublinbushelper.utils.StringUtils;

import java.util.regex.Matcher;

import javax.inject.Inject;

/**
 * Created by Ian Arbuckle on 04/03/2017.
 *
 */

public class RegisterPresenterImpl implements RegisterPresenter {

  private RegisterView view;

  @Inject
  FirebaseAuthHelper firebaseAuthHelper;

  public RegisterPresenterImpl(FirebaseAuthHelper firebaseAuthHelper) {
    this.firebaseAuthHelper = firebaseAuthHelper;
  }

  public void setView(RegisterView view) {
    this.view = view;
  }

  @Override
  public void signUpAccount(String email, String password) {
    if (StringUtils.isStringEmptyorNull(email) && !emailMatcher(email)) {
      view.showInvalidEmailMessage();
      view.hideProgressDialog();
    } else {
      firebaseAuthHelper.logOutUser();
      firebaseAuthHelper.registerUser(email, password, provideRegisterCallback());
    }
  }

  private boolean emailMatcher(String email) {
    Matcher matcher = Constants.VALID_EMAIL_ADDRESS_REGEX.matcher(email);
    return matcher.find();
  }

  private RequestListener provideRegisterCallback() {
    return new RequestListener() {
      @Override
      public void onSucess() {
        view.onSuccess();
        view.hideProgressDialog();
      }

      @Override
      public void onFailure() {
        view.onFailure();
        view.hideProgressDialog();
      }
    };
  }

  @Override
  public void validateEmail(String email) {
    view.showProgressDialog();
    if (StringUtils.isStringEmptyorNull(email)) {
      view.hideProgressDialog();
      view.showEmailEmptyMessage();
    } else if (emailMatcher(email)) {
      view.hideProgressDialog();
      view.showInvalidEmailMessage();
    }
  }

  @Override
  public void validateUsername(String username) {
    view.showProgressDialog();
    if(StringUtils.isStringEmptyorNull(username)) {
      view.hideProgressDialog();
      view.showUsernameEmptyMessage();
    } else if(username.length() < 2) {
      view.showInvalidUsernameMessage();
    }
  }

  @Override
  public void validatePassword(String password, String confirmPassword) {
    if (!StringUtils.isStringEmptyorNull(password, confirmPassword) && password.equals(confirmPassword)) {
      view.showProgressDialog();
      view.signUpOnPasswordsMatch();
    }
    else if(StringUtils.isStringEmptyorNull(password, confirmPassword)) {
      view.hideProgressDialog();
      view.showPasswordEmptyMessage();
      view.showConfirmPasswordEmptyMessage();
    }
    else {
      view.hideProgressDialog();
      view.showErrorMessage();
    }
  }
}

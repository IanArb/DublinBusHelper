package com.ianarbuckle.dublinbushelper.authentication.register;

/**
 * Created by Ian Arbuckle on 04/03/2017.
 *
 */

public interface RegisterView {
  void showInvalidEmailMessage();
  void showEmailEmptyMessage();
  void showPasswordEmptyMessage();
  void showConfirmPasswordEmptyMessage();
  void showUsernameEmptyMessage();
  void showInvalidUsernameMessage();
  void showErrorMessage();
  void showProgressDialog();
  void hideProgressDialog();
  void signUpOnPasswordsMatch();
  void onSuccess();
  void onFailure();
}

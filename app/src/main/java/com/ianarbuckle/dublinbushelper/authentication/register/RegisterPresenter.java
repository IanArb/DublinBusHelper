package com.ianarbuckle.dublinbushelper.authentication.register;

/**
 * Created by Ian Arbuckle on 04/03/2017.
 *
 */

public interface RegisterPresenter {
  void signUpAccount(String email, String password);
  void validateEmail(String email);
  void validateUsername(String username);
  void validatePassword(String password, String confirmPassword);
}

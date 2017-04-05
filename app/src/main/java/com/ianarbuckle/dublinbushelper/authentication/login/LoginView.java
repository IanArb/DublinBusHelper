package com.ianarbuckle.dublinbushelper.authentication.login;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Ian Arbuckle on 04/03/2017.
 *
 */

public interface LoginView {
  Activity getActivity();
  Context getContext();
  void onSuccess();
  void onFailure();
  void showErrorEmailMessage();
  void showErrorPasswordMessage();
  void hideProgressDialog();
  void showProgressDialog();
  void onLogin();
}

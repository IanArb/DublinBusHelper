package com.ianarbuckle.dublinbushelper.utils;

import java.util.regex.Pattern;

/**
 * Created by Ian Arbuckle on 19/02/2017.
 *
 */

public class Constants {
  public static final int PERMISSION_REQUEST_ACCESS_LOCATION = 99;
  public static final int RC_SIGN_IN = 9001;

  public static final String DIALOG_FRAGMENT = "dialog";
  public static final String POPUP_FRAGMENT = "popup";
  public static final String DISPLAY_STOP_ID_KEY = "displayStopId";
  public static final String SHORT_NAME_KEY = "shortname";
  public static final String SHORT_NAME_LOCALISED_KEY = "shortnameLocalised";
  public static final String LAST_UPDATED_KEY = "lastUpdated";
  public static final String ROUTES_KEY = "routes";
  public static final String DEBUG_LOGGER = "TAG";
  public static final String ERROR_DIALOG_FRAGMENT = "errorDialog";
  public static final String SHARED_PREFERENCES = "profile";
  public static final String NAME_KEY = "name";
  public static final String EMAIL_KEY = "email";
  public static final String PHOTO_KEY = "photoUrl";
  public static final String DEFAULT_KEY = "";
  public static final String HEADER_URL = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";

  public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
      Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
}

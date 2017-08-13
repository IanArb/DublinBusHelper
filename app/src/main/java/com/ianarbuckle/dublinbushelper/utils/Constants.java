package com.ianarbuckle.dublinbushelper.utils;

import java.util.regex.Pattern;

/**
 * Created by Ian Arbuckle on 19/02/2017.
 *
 */

public class Constants {
  public static final int PERMISSION_REQUEST_ACCESS_LOCATION = 99;
  public static final int RC_SIGN_IN = 9001;

  public static final String HEADER_URL = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";
  public static final String BASE_URL = "https://data.dublinked.ie/";

  public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
      Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

  public static final String FORMAT_TIME = "%s mins";

  public static final String DIALOG_FRAGMENT = "dialog";
  public static final String POPUP_FRAGMENT = "popup";
  public static final String SCHEDULE_FRAGMENT = "scheduleFragment";
  public static final String ERROR_DIALOG_FRAGMENT = "errorDialog";
  public static final String FAVOURITES_FRAGMENT = "favouritesFragment";

  public static final String SHARED_PREFERENCES_KEY = "profile";
  public static final String DISPLAY_STOP_ID_KEY = "displayStopId";
  public static final String SHORT_NAME_KEY = "shortname";
  public static final String SHORT_NAME_LOCALISED_KEY = "shortnameLocalised";
  public static final String LAST_UPDATED_KEY = "lastUpdated";
  public static final String ROUTES_KEY = "routes";
  public static final String NAME_KEY = "name";
  public static final String EMAIL_KEY = "email";
  public static final String PHOTO_KEY = "photoUrl";
  public static final String DEFAULT_KEY = "";
  public static final String STOPID_KEY = "stopId";
  public static final String LAT_KEY = "lat";
  public static final String LONG_KEY = "long";
  public static final String DISPLAYNAME_KEY = "displayName";
  public static final String FORMAT_KEY = "format";
  public static final String FORMAT_VALUE = "json";
  public static final String OPERATOR_KEY = "operator";

  public static final String OPERATOR_VALUE_RAIL = "ir";
  public static final String OPERATOR_VALUE_BUS = "bac";
  public static final String OPERATOR_VALUE_LUAS = "LUAS";
  public static final String IRISH_INTERNATIONAL_VALUE = "ga_IE";

  public static final String GREEN_LINE = "[Green]";
  public static final String RED_LINE = "[Red]";
  public static final String COMMUTER_ID = "[Commuter]";
  public static final String INTERCITY_ID = "[Intercity]";
  public static final String DART_COMMUTER_ID = "[DART, Commuter]";
  public static final String COMMUTER_DART_ID = "[Commuter, DART]";
  public static final String COMMUTER_INTER_DART_ID = "[Commuter, Intercity, DART]";
  public static final String COMMUTER_INTER_ID = "[Commuter, Intercity]";
  public static final String INTER_COMMUTER_ID = "[Intercity, Commuter]";
  public static final String INTER_COMMUTER_DART_ID = "[Intercity, Commuter, DART]";
  public static final String DUE_TIME_ID = "Due";

  public static final String FIREBASE_URL = "favourites";
}

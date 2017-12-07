package com.ianarbuckle.dublinbushelper.utils;

import com.ianarbuckle.dublinbushelper.search.dublinbus.route.ui.core.model.ValidationResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ian Arbuckle on 05/03/2017.
 *
 */

public class StringUtils {

  public static boolean isStringEmptyorNull(String... strings) {
    for (String current : strings) {
      if (current == null || current.isEmpty() || current.trim().isEmpty()) {
        return true;
      }
    }
    return false;
  }

  public static String timeFormatter(String time) {
    return time + Constants.FORMAT_TIME;
  }

  public static boolean isAlphaNumeric(String string) {
    Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z._0-9]{2,19}$");
    Matcher matcher = pattern.matcher(string);
    return matcher.find();
  }

  public static ValidationResult<String> isRouteIdValid(String routeId) {
    if (isStringEmptyorNull(routeId)) {
      return ValidationResult.failure(null, routeId);
    }

    if(isAlphaNumeric(routeId)) {
      return ValidationResult.success(routeId);
    }

    return ValidationResult.failure("Route id should only contain alphanumeric characters", routeId);
  }

}

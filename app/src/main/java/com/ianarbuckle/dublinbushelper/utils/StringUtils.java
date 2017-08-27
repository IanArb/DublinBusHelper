package com.ianarbuckle.dublinbushelper.utils;

/**
 * Created by Ian Arbuckle on 05/03/2017.
 *
 */

public class StringUtils {

  public static boolean isStringEmptyorNull(String... strings) {
    for(String current : strings) {
      if(current == null || current.isEmpty() || current.trim().isEmpty()) {
        return true;
      }
    }
    return false;
  }

  public static String timeFormatter(String time) {
    return time + Constants.FORMAT_TIME;
  }

}

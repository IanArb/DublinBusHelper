package com.ianarbuckle.dublinbushelper.transports.schedules;

/**
 * Created by Ian Arbuckle on 27/07/2017.
 *
 */

public interface ScheduleRowView {
  void setDestination(String destination);
  void setDueTime(String dueTime);
  void setDirection(String direction);
}

package com.ianarbuckle.dublinbushelper.search.searchlist.route;

import com.ianarbuckle.dublinbushelper.models.routeinformation.Result;

import java.util.List;

/**
 * Created by Ian Arbuckle on 30/10/2017.
 *
 */

public interface RouteSearchListView {
  void setResults(List<Result> results);
  void showError();
  void showProgress();
  void hideProgress();
}

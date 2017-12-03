package com.ianarbuckle.dublinbushelper.search.searchlist.stop.ui.core.interactor;

import com.ianarbuckle.dublinbushelper.models.stopinfo.StopInformation;

import rx.Observable;

/**
 * Created by Ian Arbuckle on 24/09/2017.
 *
 */

public interface StopSearchInteractor {
  Observable<StopInformation> fetchStopInformation();
}

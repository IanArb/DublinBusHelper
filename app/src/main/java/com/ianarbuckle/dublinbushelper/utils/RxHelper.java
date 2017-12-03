package com.ianarbuckle.dublinbushelper.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;


import javax.annotation.Nonnull;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/**
 * Created by Ian Arbuckle on 19/11/2017.
 *
 */

public class RxHelper {

  public static Observable<String> getTextWatcherObservable(@Nonnull EditText editText) {
    final PublishSubject<String> subject = PublishSubject.create();

    editText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        subject.onNext(s.toString());
      }
    });

    return subject;
  }
}

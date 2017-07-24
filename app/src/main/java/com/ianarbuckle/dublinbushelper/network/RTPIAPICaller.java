package com.ianarbuckle.dublinbushelper.network;

import com.ianarbuckle.dublinbushelper.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by Ian Arbuckle on 20/02/2017.
 *
 */

public class RTPIAPICaller {

  public static RTPIServiceAPI getRTPIServiceAPI() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build();

    return retrofit.create(RTPIServiceAPI.class);
  }
}

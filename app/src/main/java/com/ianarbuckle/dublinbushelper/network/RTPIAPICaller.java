package com.ianarbuckle.dublinbushelper.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ian Arbuckle on 20/02/2017.
 *
 */

public class RTPIAPICaller {

  private static final String BASE_URL = "https://data.dublinked.ie/";

  public static RTPIServiceAPI getRTPIServiceAPI() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    return retrofit.create(RTPIServiceAPI.class);
  }
}

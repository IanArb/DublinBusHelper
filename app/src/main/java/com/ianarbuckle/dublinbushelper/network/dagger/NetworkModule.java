package com.ianarbuckle.dublinbushelper.network.dagger;

import android.content.Context;

import com.ianarbuckle.dublinbushelper.ApplicationScope;
import com.ianarbuckle.dublinbushelper.network.RealTimePassengerInfoService;
import com.ianarbuckle.dublinbushelper.network.RealTimePassengerInfoAPI;
import com.ianarbuckle.dublinbushelper.utils.Constants;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by Ian Arbuckle on 13/08/2017.
 *
 */
@Module
public class NetworkModule {

  @Provides
  @ApplicationScope
  public Cache provideCache(Context context) {
    return new Cache(new File(context.getCacheDir(), Constants.HTTP_CACHE_DIR), Constants.HTTP_CACHE_SIZE);
  }

  @Provides
  @ApplicationScope
  public OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor, Cache cache) {
    return new OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .cache(cache)
        .build();
  }

  @Provides
  @ApplicationScope
  public HttpLoggingInterceptor providesLoggingInterceptor() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> {
    });
    interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
    return interceptor;
  }

  @Provides
  @ApplicationScope
  public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
    return new Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
  }

  @Provides
  @ApplicationScope
  public RealTimePassengerInfoService provideRTPIService(Retrofit retrofit) {
    return retrofit.create(RealTimePassengerInfoService.class);
  }

  @Provides
  @ApplicationScope
  public RealTimePassengerInfoAPI provideNetworkClient(RealTimePassengerInfoService service) {
    return new RealTimePassengerInfoAPI(service);
  }

}

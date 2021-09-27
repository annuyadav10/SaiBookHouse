package com.appsnipp.claraerp.saibookhouse.Network;
import com.appsnipp.claraerp.saibookhouse.Utils.Constant;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class APIClient {

   private static Retrofit retrofit=null;

   public static Retrofit getclient(){

       HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
       interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
       retrofit = new Retrofit.Builder()
               .baseUrl(Constant.BaseUrl)
               .addConverterFactory(GsonConverterFactory.create())
               .build();
       return retrofit;

   }
}

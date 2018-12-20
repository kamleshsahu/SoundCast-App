package com.kamlesh.soundcastapp;

import com.google.gson.Gson;
import com.kamlesh.soundcastapp.Model.Apidata;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Test {

    public static void main(String... args){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                //.addInterceptor(loggingInterceptor)
                //.addNetworkInterceptor(networkInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://parseapi.back4app.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiService = retrofit.create(ApiInterface.class);
        Call<Apidata> stringCall = apiService.inputCall();


        try {

            System.out.println(new Gson().toJson(stringCall.execute().body()));
  //          System.out.println(stringCall.execute().body().getResults().get(0).getLink());
//            apiService.inputCall().enqueue(new Callback<Apidata>() {
//                @Override
//                public void onResponse(Call call, Response response) {
//                 //   System.out.println(response.body());
//                   Apidata data= (Apidata) response.body();
//
//
//                }
//
//                @Override
//                public void onFailure(Call call, Throwable throwable) {
//                    System.out.println(throwable.getMessage());
//                }
//            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

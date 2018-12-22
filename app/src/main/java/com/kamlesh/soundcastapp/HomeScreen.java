package com.kamlesh.soundcastapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kamlesh.soundcastapp.Adapters.AdapterList;
import com.kamlesh.soundcastapp.Model.Apidata;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeScreen extends AppCompatActivity {

    RecyclerView recyclerView=null;
    static Apidata apidata=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        recyclerView=findViewById(R.id.songList);

        (findViewById(R.id.addmusic)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this,AddMusic.class));
            }
        });

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

        try {

            apiService.inputCall().enqueue(new Callback<Apidata>() {
                @Override
                public void onResponse(Call call, Response response) {
            //        System.out.println(response.body());

                    LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setHasFixedSize(true);
                    apidata= (Apidata) response.body();
                    AdapterList adapterList = new AdapterList(getApplicationContext(),HomeScreen.this,apidata.getResults() );

                    recyclerView.setAdapter(adapterList);
                }

                @Override
                public void onFailure(Call call, Throwable throwable) {
                    System.out.println(throwable.getMessage());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
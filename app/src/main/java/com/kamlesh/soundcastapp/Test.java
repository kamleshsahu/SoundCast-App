package com.kamlesh.soundcastapp;

import android.widget.Toast;

import com.google.gson.Gson;
import com.kamlesh.soundcastapp.Model.DownloadModel.Apidata;
import com.kamlesh.soundcastapp.Model.UploadModel.Music_file;
import com.kamlesh.soundcastapp.Model.UploadModel.Response;
import com.kamlesh.soundcastapp.Model.UploadModel.Song;
import com.kamlesh.soundcastapp.Model.UploadModel.Thumbnail_file;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Test {

    public static void main(String... args){





        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, " { \"title\": \""+"abcd"+"\",\n            \"link\": \"A string\",\n            \"thumbnail\": \"A string\",\n            \"music_file\": {\n                \"__type\": \"File\",\n                \"name\": \"1f089358a107d867edc88526baf7e5ac_fast-and-furious.mp3\",\n                \"url\":\"fdhfd\"\n            \n            },\n            \"thumbnail_file\": {\n                \"__type\": \"File\",\n                \"name\": \"eb3dde0688004d4414d5c61cf808e5e4_fast-and-furious.jpg\",\n                \"url\":\"dfaghs\"\n            }\n }\n\n\n \n");
        Request request = new Request.Builder()
                .url("https://soundcast.back4app.io/classes/songs_library")
                .post(body)
                .addHeader("x-parse-application-id", "VSPdpDKRMND382hqIRFIaiVLgbkhM0E1rL32l1SQ")
                .addHeader("x-parse-rest-api-key", "E4ZeObhQv3XoHaQ3Q6baHGgbDPOkuO9jPlY9gzgA")
                .addHeader("content-type", "application/json")
        //        .addHeader("cache-control", "no-cache")
        //        .addHeader("postman-token", "cf07c74b-5917-6161-897d-2884bf9ad99a")
                .build();

        try {
            okhttp3.Response response = client.newCall(request).execute();
            System.out.println(new Gson().toJson(response.body().string()));

        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.toString());

        }

//
//                OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .readTimeout(120, TimeUnit.SECONDS)
//                .writeTimeout(120, TimeUnit.SECONDS)
//                .connectTimeout(120, TimeUnit.SECONDS)
//                //.addInterceptor(loggingInterceptor)
//                //.addNetworkInterceptor(networkInterceptor)
//                .build();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://soundcast.back4app.io/")
//                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        ApiInterface apiService = retrofit.create(ApiInterface.class);


//        try {
//
//            String songstr=" { \"title\": \""+newsong.getTitle()+"\",\n            \"link\": \"A string\",\n            \"thumbnail\": \"A string\",\n            \"music_file\": {\n                \"__type\": \"File\",\n                \"name\": \"1f089358a107d867edc88526baf7e5ac_fast-and-furious.mp3\",\n                \"url\":\"fdhfd\"\n            \n            },\n            \"thumbnail_file\": {\n                \"__type\": \"File\",\n                \"name\": \"eb3dde0688004d4414d5c61cf808e5e4_fast-and-furious.jpg\",\n                \"url\":\"dfaghs\"\n            }\n }\n\n\n \n";
////            System.out.println(new Gson().toJson(stringCall.execute().body()));
////            System.out.println(stringCall.execute().body().getResults().get(0).getLink());
//            apiService.uploadSong(new Gson().toJson(newsong)).enqueue(new Callback<Response>() {
//                @Override
//                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//                    System.out.println("got the data:");
//                    System.out.println(response.body());
//                    System.out.println(response.code());
//                    System.out.println(response.message());
//                    System.out.println(response.errorBody());
//                }
//
//                @Override
//                public void onFailure(Call<Response> call, Throwable throwable) {
//
//                }
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}

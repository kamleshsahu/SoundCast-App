package com.kamlesh.soundcastapp;



import com.kamlesh.soundcastapp.Model.Apidata;
import com.kamlesh.soundcastapp.Model.Result;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    
    @Headers({
            "Content-Type: application/json",
            "X-Parse-Application-Id:VSPdpDKRMND382hqIRFIaiVLgbkhM0E1rL32l1SQ",
            "X-Parse-REST-API-Key:E4ZeObhQv3XoHaQ3Q6baHGgbDPOkuO9jPlY9gzgA"})
 //   @POST("dev/")
    @GET("classes/songs_library")
    Call<Apidata> inputCall();

    @POST("classes/songs_library")
    Call<Object> uploadSong(@Body Result song);
}

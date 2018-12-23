package com.kamlesh.soundcastapp;



import com.kamlesh.soundcastapp.Model.DownloadModel.Apidata;
import com.kamlesh.soundcastapp.Model.UploadModel.Response;
import com.kamlesh.soundcastapp.Model.UploadModel.Song;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    
    @Headers({
            "X-Parse-Application-Id:VSPdpDKRMND382hqIRFIaiVLgbkhM0E1rL32l1SQ",
            "X-Parse-REST-API-Key:E4ZeObhQv3XoHaQ3Q6baHGgbDPOkuO9jPlY9gzgA",
            "Content-Type:application/json",
            "cache-control:no-cache"
    })

    @GET("classes/songs_library")
    Call<Apidata> inputCall();

    @POST("classes/songs_library")
    Call<Response> uploadSong(@Body String song);
}

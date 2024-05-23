package com.example.assignment9_cameraserver;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {
    String webServerUrl = "https://6d6c-114-125-126-68.ngrok-free.app/";

    @Multipart
    @POST("/")
    Call<ServerResponse> uploadImage(@Part MultipartBody.Part image);
}

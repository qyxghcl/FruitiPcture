package com.example.fruitpictures.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by hwj on 2017/2/11.
 */

public interface ImageServices {
    @GET("{page}")
    Call<String> loagImages(@Path("page") String page);
}

package com.example.popovov.newyorktimes.network;


import com.example.popovov.newyorktimes.model.Answer;
import com.example.popovov.newyorktimes.model.Result;
import com.example.popovov.newyorktimes.utils.AppConfig;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ApiService {
    @Headers("Content-Type: application/json")
    @GET("{path}/all-sections/1.json?api-key="+ AppConfig.Api_Key)
    Call<Answer> getMyJson(@Path("path") String path);
    //mostemailed
     //Call<Answer> getMyJson();
}

    package com.example.popovov.newyorktimes.network;

    import com.example.popovov.newyorktimes.utils.AppConfig;
    import retrofit2.Retrofit;
    import retrofit2.converter.gson.GsonConverterFactory;

    public class RetroClient {


        private Retrofit getRetrofitInstance(){

            return new Retrofit.Builder()
                    .baseUrl(AppConfig.Base_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        public  ApiService getApiService(){
            return getRetrofitInstance().create(ApiService.class);

        }

}

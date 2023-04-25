package vn.tdc.edu.fooddelivery.api.builder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(){

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .build();
        if(retrofit ==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.125/food-delivery-api/public/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return  retrofit;
    }
}

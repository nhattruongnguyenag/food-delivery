package vn.tdc.edu.fooddelivery.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final String BASE_URL = "http://192.168.222.106/food-delivery-api/public/";
    private static RetrofitClient modelInstance;
    private Retrofit retrofit;

    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitClient getInstance() {
        if (modelInstance == null) {
            modelInstance = new RetrofitClient();
        }

        return  modelInstance;
    }

    public ProductAPI getProductAPI() {
        return retrofit.create(ProductAPI.class);
    }
}

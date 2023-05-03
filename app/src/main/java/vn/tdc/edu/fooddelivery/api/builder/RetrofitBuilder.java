package vn.tdc.edu.fooddelivery.api.builder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.tdc.edu.fooddelivery.system.SystemConstant;

public class RetrofitBuilder {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(){

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        if(retrofit ==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(SystemConstant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return  retrofit;
    }
}

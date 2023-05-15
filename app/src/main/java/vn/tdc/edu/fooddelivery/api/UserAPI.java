package vn.tdc.edu.fooddelivery.api;

import android.content.Intent;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import vn.tdc.edu.fooddelivery.models.ProductModel;
import vn.tdc.edu.fooddelivery.models.UserModel;

public interface UserAPI {
    @GET("api/users")
    Call<List<UserModel>> getAll(@Query("roleCode") String roleCode);

    @POST("api/users")
    Call<UserModel> save(@Body UserModel userModel);

    @PUT("api/users")
    Call<UserModel> update(@Body UserModel userModel);

    @HTTP(method = "DELETE", path = "api/users", hasBody = true)
    Call<UserModel> delete(@Body UserModel userModel);
}

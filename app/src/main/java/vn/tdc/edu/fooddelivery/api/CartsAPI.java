package vn.tdc.edu.fooddelivery.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.tdc.edu.fooddelivery.models.ItemCartsModel;
import vn.tdc.edu.fooddelivery.models.CartsModel;

public interface CartsAPI {
    @GET("api/carts")
    Call<List<CartsModel>> findCartsOfUser(@Query("userId") Integer userId);

    @POST("api/carts")
    Call<List<CartsModel>> updateAndCreate(@Body ItemCartsModel carstModel);

    @DELETE("api/carts/{id}")
    Call<CartsModel> delete(@Path("id") Integer id);
}

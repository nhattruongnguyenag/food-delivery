package vn.tdc.edu.fooddelivery.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.tdc.edu.fooddelivery.models.AddCarstModel;
import vn.tdc.edu.fooddelivery.models.CarstModel;
import vn.tdc.edu.fooddelivery.models.CategoryModel;
import vn.tdc.edu.fooddelivery.models.NotificationModel;
import vn.tdc.edu.fooddelivery.models.ProductModel;
import vn.tdc.edu.fooddelivery.models.UserModel;

public interface CartsAPI {
    @GET("api/carts")
    Call<List<CarstModel>> findCartsOfUser(@Query("userId") Integer userId);

    @POST("api/carts")
    Call<List<CarstModel>> updateAndCreate(@Body AddCarstModel carstModel);

    @DELETE("api/carts/{id}")
    Call<CarstModel> delete(@Path("id") Integer id);
}

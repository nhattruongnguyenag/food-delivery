package vn.tdc.edu.fooddelivery.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.tdc.edu.fooddelivery.models.CategoryModel;
import vn.tdc.edu.fooddelivery.models.OrderModel;

public interface OrderAPI {
    @GET("api/orders")
    Call<List<OrderModel>> findAll(@Query("status") Integer status);

    @POST("api/orders")
    Call<OrderModel> save(@Body OrderModel orderModel);

    @PUT("api/orders")
    Call<OrderModel> update(@Body OrderModel orderModel);

    @DELETE("api/orders/{id}")
    Call<OrderModel> delete(@Path("id") Integer id);
}

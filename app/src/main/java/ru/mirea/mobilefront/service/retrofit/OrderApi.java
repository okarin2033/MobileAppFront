package ru.mirea.mobilefront.service.retrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import ru.mirea.mobilefront.dto.OrderDto;

public interface OrderApi {
    @POST("/order/new")
    public Call<Boolean> addOrder(@Header("token") String token, @Body OrderDto orderDto);

    @GET("/order/get")
    public Call<List<OrderDto>> getClientOrderList(@Header("token") String token);
}

package ru.mirea.mobilefront.service;

import android.os.Build;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ru.mirea.mobilefront.dto.OrderDto;
import ru.mirea.mobilefront.service.retrofit.OrderApi;

public class OrderService {
    @Getter
    static MutableLiveData<List<OrderDto>> orderData= new MutableLiveData<>();
    static Retrofit retrofit = new Retrofit.Builder().baseUrl(Configuration.SERVER_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();
    static OrderApi orderApi = retrofit.create(OrderApi.class);

    public static void sendOrder(OrderDto orderDto){
        Call<Boolean> call = orderApi.addOrder(UserSession.getUserSession().getValue().getToken(), orderDto);
        final Boolean[] status = new Boolean[1];
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                System.out.println("Зазказ отправлен на сервер");
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                throw new RuntimeException("Server Fail");
            }
        });
    }

    public static void getOrders(){
        Call<List<OrderDto>> call = orderApi.getClientOrderList(UserSession.getUserSession().getValue().getToken());
        call.enqueue(new Callback<List<OrderDto>>() {
            @Override
            public void onResponse(Call<List<OrderDto>> call, Response<List<OrderDto>> response) {
                orderData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<OrderDto>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public static double getOrdersSum(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return orderData.getValue().stream().mapToDouble(o -> o.getFullPrice()).sum();
        }
        else return 0;
    }

}

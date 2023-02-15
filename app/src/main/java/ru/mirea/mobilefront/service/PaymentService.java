package ru.mirea.mobilefront.service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ru.mirea.mobilefront.dto.OrderDto;
import ru.mirea.mobilefront.service.retrofit.BookApi;
import ru.mirea.mobilefront.service.retrofit.OrderApi;

public class PaymentService {
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

}

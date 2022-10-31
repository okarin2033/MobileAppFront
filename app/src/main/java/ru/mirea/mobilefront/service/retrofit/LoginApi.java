package ru.mirea.mobilefront.service.retrofit;


import android.util.Log;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import ru.mirea.mobilefront.dto.LoginFormDto;
import ru.mirea.mobilefront.dto.TokenDto;

public interface LoginApi {
    @GET("/test")
    Call<LoginFormDto> getLoginForm();

    @POST("/login")
    Call<TokenDto> login(@Body LoginFormDto dto);
}

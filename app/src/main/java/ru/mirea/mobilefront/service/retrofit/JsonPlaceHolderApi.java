package ru.mirea.mobilefront.service.retrofit;


import retrofit2.Call;
import retrofit2.http.GET;
import ru.mirea.mobilefront.dto.LoginFormDto;

public interface JsonPlaceHolderApi {
    @GET("/test")
    Call<LoginFormDto> getLoginForm();

}

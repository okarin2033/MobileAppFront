package ru.mirea.mobilefront.service.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import ru.mirea.mobilefront.dto.TokenDto;
import ru.mirea.mobilefront.dto.UserDto;

public interface UserApi {

    @POST("/verify")
    Call<UserDto> getAuthUser(@Body TokenDto tokenDto);

    @PUT("/update/phone")
    Call<UserDto> updatePhone(@Header("token") String token, @Body UserDto userDto);

    @PUT("/update/address")
    Call<UserDto> updateAddress(@Header("token") String token, @Body UserDto userDto);

}

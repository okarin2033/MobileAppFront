package ru.mirea.mobilefront.service.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import ru.mirea.mobilefront.dto.TokenDto;
import ru.mirea.mobilefront.dto.UserDto;

public interface UserApi {

    @POST("/verify")
    Call<UserDto> getAuthUser(@Body TokenDto tokenDto);

}

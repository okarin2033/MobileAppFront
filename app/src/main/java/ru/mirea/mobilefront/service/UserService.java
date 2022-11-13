package ru.mirea.mobilefront.service;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ru.mirea.mobilefront.dto.LoginFormDto;
import ru.mirea.mobilefront.dto.RoleEnum;
import ru.mirea.mobilefront.dto.TokenDto;
import ru.mirea.mobilefront.dto.UserDto;
import ru.mirea.mobilefront.service.retrofit.BookApi;
import ru.mirea.mobilefront.service.retrofit.UserApi;

public class UserService {

    Retrofit retrofit = new Retrofit.Builder().baseUrl(Configuration.SERVER_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();
    UserApi userApi = retrofit.create(UserApi.class);
    MutableLiveData<UserSession> sessionData = UserSession.getUserSession();


    public void getUserData(String token) {
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(token);
        tokenDto.setRole(RoleEnum.USER_ROLE);
        Call<UserDto> call= userApi.getAuthUser(tokenDto);
        call.enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                UserSession userSession = new UserSession();
                userSession.setRole(response.body().getRole());
                userSession.setToken(token);
                userSession.setUsername(response.body().getUsername());
                userSession.setEmail(response.body().getEmail());
                sessionData.postValue(userSession);
                Log.d("auth", userSession.toString());

            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                Log.d("error", "Error on getting User Data with token");
            }
        });
    }
}

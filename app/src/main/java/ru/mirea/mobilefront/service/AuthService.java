package ru.mirea.mobilefront.service;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.Objects;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ru.mirea.mobilefront.MainActivity;
import ru.mirea.mobilefront.dto.LoginFormDto;
import ru.mirea.mobilefront.dto.TokenDto;
import ru.mirea.mobilefront.service.retrofit.LoginApi;

public class AuthService {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(Configuration.SERVER_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();
    LoginApi loginApi = retrofit.create(LoginApi.class);

    private static final MutableLiveData<String> LoginLiveData = new MutableLiveData<String>();

    //@Getter
    //private static String userToken = null;

    public LoginFormDto test() {
        LoginFormDto loginFormDto = new LoginFormDto();
        Call<LoginFormDto> call = loginApi.getLoginForm();
         call.enqueue(new Callback<LoginFormDto>() {
            @Override
            public void onResponse(Call<LoginFormDto> call, Response<LoginFormDto> response) {
                System.out.println(loginFormDto);
            }

            @Override
            public void onFailure(Call<LoginFormDto> call, Throwable t) {
                System.out.println("Error on get request");
                t.printStackTrace();
            }
        });
        return  loginFormDto;
    }

    public void login(String username, String password) {
        LoginFormDto dto = new LoginFormDto();
        dto.setPassword(password);
        dto.setUsername(username);
        Call<TokenDto> call= loginApi.login(dto);
        call.enqueue(new Callback<TokenDto>() {
            @Override
            public void onResponse(Call<TokenDto> call, Response<TokenDto> response) {
                if (response.body()!=null) {
                    TokenDto tokenDto = response.body();
                    System.out.println(tokenDto.getToken());
                    //userToken = tokenDto.getToken();
                    LoginLiveData.postValue(tokenDto.getToken());
                } else LoginLiveData.postValue("errorLogin");
            }

            @Override
            public void onFailure(Call<TokenDto> call, Throwable t) {
                System.out.println("Wrong email or password");
                LoginLiveData.postValue("errorLogin");
            }
        });
    }

    public static MutableLiveData<String> getLiveData(){
        return LoginLiveData;
    }
}

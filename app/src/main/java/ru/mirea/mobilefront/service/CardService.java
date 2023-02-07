package ru.mirea.mobilefront.service;

import androidx.lifecycle.MutableLiveData;

import javax.xml.transform.Source;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ru.mirea.mobilefront.dto.CardDto;
import ru.mirea.mobilefront.service.retrofit.BookApi;
import ru.mirea.mobilefront.service.retrofit.UserApi;

public class CardService {

    static Retrofit retrofit = new Retrofit.Builder().baseUrl(Configuration.SERVER_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();
    static UserApi userApi = retrofit.create(UserApi.class);

    @Getter
    static MutableLiveData<CardDto> bonusCard = new MutableLiveData<>();

    public static void getCardData(){
        Call<CardDto> cardCall = userApi.getCard(UserSession.getUserSession().getValue().getToken());
        cardCall.enqueue(new Callback<CardDto>() {
            @Override
            public void onResponse(Call<CardDto> call, Response<CardDto> response) {
                CardDto card = response.body();
                bonusCard.postValue(card);
            }

            @Override
            public void onFailure(Call<CardDto> call, Throwable t) {
                System.out.println("Server timeout");
            }
        });
    }

    public static void updateCardData(int bonus, int level){
        CardDto cardDto = new CardDto();
        cardDto.setBonus(bonus);
        cardDto.setPower(level);
        Call<CardDto> cardCall = userApi.updateCard(UserSession.getUserSession().getValue().getToken(), cardDto);
        cardCall.enqueue(new Callback<CardDto>() {
            @Override
            public void onResponse(Call<CardDto> call, Response<CardDto> response) {
                CardDto card = response.body();
                bonusCard.postValue(card);
            }

            @Override
            public void onFailure(Call<CardDto> call, Throwable t) {
                System.out.println("Server timeout");
            }
        });

    }

}

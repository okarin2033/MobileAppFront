package ru.mirea.mobilefront.service;

import android.util.Log;

import androidx.arch.core.internal.SafeIterableMap;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ru.mirea.mobilefront.dto.Book;
import ru.mirea.mobilefront.dto.BookSimple;
import ru.mirea.mobilefront.service.retrofit.BookApi;

public class BookService {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(Configuration.SERVER_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();
    BookApi bookApi = retrofit.create(BookApi.class);

    @Getter
    private static final MutableLiveData<List<BookSimple>> bestBooksList = new MutableLiveData<>();
    @Getter
    private static final MutableLiveData<List<BookSimple>> topBooksList = new MutableLiveData<>();

    public void updateBestBooks(){
        List<BookSimple> bookList = new ArrayList<>();
        Call<List<BookSimple>> call = bookApi.getBestBooks();
        call.enqueue(new Callback<List<BookSimple>>() {
            @Override
            public void onResponse(Call<List<BookSimple>> call, Response<List<BookSimple>> response) {
                bestBooksList.postValue(response.body());
                System.out.println(bestBooksList);
            }

            @Override
            public void onFailure(Call<List<BookSimple>> call, Throwable t) {
                Log.println(Log.ASSERT, "error", "error with receiving data from server in updateBestBooks");
                t.printStackTrace();
            }
        });
    }

    public void updateTopList(){
        List<BookSimple> bookList = new ArrayList<>();
        Call<List<BookSimple>> call = bookApi.getNewBooks();
        call.enqueue(new Callback<List<BookSimple>>() {
            @Override
            public void onResponse(Call<List<BookSimple>> call, Response<List<BookSimple>> response) {
                bestBooksList.postValue(response.body());
                System.out.println(bestBooksList);
            }

            @Override
            public void onFailure(Call<List<BookSimple>> call, Throwable t) {
                Log.println(Log.ASSERT, "error", "error with receiving data from server in updateTopBooks");
                t.printStackTrace();
            }
        });
    }

}

package ru.mirea.mobilefront.service;

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
import ru.mirea.mobilefront.dto.BookFull;
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
    private static final MutableLiveData<List<BookSimple>> newBooksList = new MutableLiveData<>();

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

    public void updateNewBooks(){
        Call<List<BookSimple>> call = bookApi.getNewBooks();
        call.enqueue(new Callback<List<BookSimple>>() {
            @Override
            public void onResponse(Call<List<BookSimple>> call, Response<List<BookSimple>> response) {
                newBooksList.postValue(response.body());
                System.out.println(bestBooksList);
            }

            @Override
            public void onFailure(Call<List<BookSimple>> call, Throwable t) {
                Log.println(Log.ASSERT, "error", "error with receiving data from server in updateTopBooks");
                t.printStackTrace();
            }
        });
    }

    @Getter
    private static final MutableLiveData<List<BookSimple>> searchBookList = new MutableLiveData<List<BookSimple>>();

    public void searchForBook(String request){
        Call<List<BookSimple>> call = bookApi.searchForBook(request);
        call.enqueue(new Callback<List<BookSimple>>() {
            @Override
            public void onResponse(Call<List<BookSimple>> call, Response<List<BookSimple>> response) {
                searchBookList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<BookSimple>> call, Throwable t) {
                Log.d("error", "error on receiving data from server in searchForBook");
                t.printStackTrace();
            }
        });
    }

    @Getter
    private static final MutableLiveData<BookFull> fullBookData =  new MutableLiveData<BookFull>();
    public void getFullBookData(String url) {
        Call<BookFull> call = bookApi.getBook(url);
        call.enqueue(new Callback<BookFull>() {
            @Override
            public void onResponse(Call<BookFull> call, Response<BookFull> response) {
                BookFull book = response.body();
                fullBookData.postValue(book);
            }

            @Override
            public void onFailure(Call<BookFull> call, Throwable t) {
                Log.d("error", "error on receiving data from server in getFullBookData");
            }
        });
    }
}

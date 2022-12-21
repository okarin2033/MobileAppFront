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
    static Retrofit retrofit = new Retrofit.Builder().baseUrl(Configuration.SERVER_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();
    static BookApi bookApi = retrofit.create(BookApi.class);

    @Getter
    private static final MutableLiveData<List<BookSimple>> bestBooksList = new MutableLiveData<>();
    @Getter
    private static final MutableLiveData<List<BookSimple>> newBooksList = new MutableLiveData<>();
    @Getter
    private static final MutableLiveData<BookFull> currentChosenBook = new MutableLiveData<>();

    public static void updateBestBooks(){
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

    public static void updateNewBooks(){
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

    public static void searchForBook(String request){
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

    public static void getFullBookData(String url) {

        {
            int startIndex = url.indexOf("product/") + "product/".length();
            int endIndex = url.lastIndexOf("/");
            url = url.substring(startIndex, endIndex);
        } //String reformatting

        Call<BookFull> call = bookApi.getBook(url);

        call.enqueue(new Callback<BookFull>() {
            @Override
            public void onResponse(Call<BookFull> call, Response<BookFull> response) {
                BookFull book = response.body();
                System.out.println(book);
                currentChosenBook.postValue(book);
            }

            @Override
            public void onFailure(Call<BookFull> call, Throwable t) {
                Log.d("error", "error on receiving data from server in getFullBookData");
            }
        });
    }
}

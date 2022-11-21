package ru.mirea.mobilefront.service.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.mirea.mobilefront.dto.BookFull;
import ru.mirea.mobilefront.dto.BookSimple;
import ru.mirea.mobilefront.dto.LoginFormDto;

public interface BookApi {
    @GET("/book/new")
    Call<List<BookSimple>> getNewBooks();
    @GET("/book/best")
    Call<List<BookSimple>> getBestBooks();
    @GET("/book/search/{name}")
    Call<List<BookSimple>> searchForBook(@Path("name") String bookName);
    @GET("/book/get/{url}")
    Call<BookFull> getBook(@Path("url") String url);

}

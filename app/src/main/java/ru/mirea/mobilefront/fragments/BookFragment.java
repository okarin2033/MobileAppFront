package ru.mirea.mobilefront.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.mobilefront.R;
import ru.mirea.mobilefront.design.BookViewAdapter;
import ru.mirea.mobilefront.dto.BookSimple;
import ru.mirea.mobilefront.service.BookService;

public class BookFragment extends Fragment {
    BookViewAdapter bookViewAdapter;
    RecyclerView newBooksView;
    RecyclerView topBooksView;
    BookService bookService = new BookService();
    MutableLiveData<List<BookSimple>> newBooksList;
    MutableLiveData<List<BookSimple>> topBooksList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (ViewGroup)inflater
                .inflate(R.layout.fragment_book,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Новые книги, заполнение витрины
        newBooksView = view.findViewById(R.id.new_books_view);
        topBooksView = view.findViewById(R.id.top_books_view);
        newBooksList = BookService.getNewBooksList();
        topBooksList = BookService.getBestBooksList();

        RecyclerView.LayoutManager layoutManager = new
                LinearLayoutManager(view.getContext()
                ,RecyclerView.HORIZONTAL
                ,false);
        newBooksView.setLayoutManager(layoutManager);
        RecyclerView.LayoutManager layoutManager2 = new
                LinearLayoutManager(view.getContext()
                ,RecyclerView.HORIZONTAL
                ,false);
        topBooksView.setLayoutManager(layoutManager2);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), RecyclerView.HORIZONTAL);
        Drawable d = ResourcesCompat.getDrawable(this.getResources(), R.drawable.divider_horizontal_res, null);
        dividerItemDecoration.setDrawable(d);
        newBooksView.addItemDecoration(dividerItemDecoration);
        topBooksView.addItemDecoration(dividerItemDecoration);

        newBooksList.observe(getViewLifecycleOwner(), new Observer<List<BookSimple>>() {
            @Override
            public void onChanged(List<BookSimple> newBooks) {
                //for (BookSimple book: bookSimples) System.out.println(book.toString());
                bookViewAdapter = new BookViewAdapter(view.getContext(), newBooks);
                newBooksView.setAdapter(bookViewAdapter);
            }
        });

        topBooksList.observe(getViewLifecycleOwner(), new Observer<List<BookSimple>>() {
            @Override
            public void onChanged(List<BookSimple> bookSimples) {
                bookViewAdapter = new BookViewAdapter(view.getContext(), bookSimples);
                topBooksView.setAdapter(bookViewAdapter);
            }
        });

        bookService.updateBestBooks();
        bookService.updateNewBooks();
    }

}
package ru.mirea.mobilefront.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.mobilefront.R;
import ru.mirea.mobilefront.design.BookViewAdapter;
import ru.mirea.mobilefront.dto.BookSimple;
import ru.mirea.mobilefront.service.BookService;

public class book_fragment extends Fragment {
    BookViewAdapter bookViewAdapter;
    RecyclerView newBooksView;
    BookService bookService = new BookService();
    MutableLiveData<List<BookSimple>> newBooksList;
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
        newBooksList = BookService.getBestBooksList();

        newBooksList.observe(getViewLifecycleOwner(), new Observer<List<BookSimple>>() {
            @Override
            public void onChanged(List<BookSimple> newBooks) {
                //for (BookSimple book: bookSimples) System.out.println(book.toString());
                RecyclerView.LayoutManager layoutManager = new
                        LinearLayoutManager(view.getContext()
                        ,RecyclerView.HORIZONTAL
                        ,false);
                newBooksView.setLayoutManager(layoutManager);
                bookViewAdapter = new BookViewAdapter(view.getContext(), newBooks);
                newBooksView.setAdapter(bookViewAdapter);
            }
        });
        bookService.updateBestBooks();
        bookService.updateNewBooks();
    }

}
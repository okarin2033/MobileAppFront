package ru.mirea.mobilefront.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.List;

import ru.mirea.mobilefront.R;
import ru.mirea.mobilefront.dto.BookSimple;
import ru.mirea.mobilefront.service.BookService;

public class book_fragment extends Fragment {
    TextView bookText;
    BookService bookService = new BookService();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (ViewGroup)inflater
                .inflate(R.layout.fragment_book,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //bookText.setText("Test text");
        MutableLiveData<List<BookSimple>> bookRepo = BookService.getBestBooksList();
        bookRepo.observe(getViewLifecycleOwner(), new Observer<List<BookSimple>>() {
            @Override
            public void onChanged(List<BookSimple> bookSimples) {
                for (BookSimple book: bookSimples) System.out.println(book.toString());
            }
        });
        bookService.updateBestBooks();
    }
}
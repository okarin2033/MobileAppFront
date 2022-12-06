package ru.mirea.mobilefront.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import lombok.Getter;
import lombok.Setter;
import ru.mirea.mobilefront.R;
import ru.mirea.mobilefront.databinding.FragmentFullBookBinding;
import ru.mirea.mobilefront.dto.BookFull;


public class FullBookFragment extends Fragment {
    @Setter
    @Getter
    BookFull currentBook;
    ConstraintLayout constraintLayout;
    FragmentFullBookBinding fullBookBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fullBookBinding = FragmentFullBookBinding.inflate(inflater);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_full_book, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }


    public void updateBook(BookFull bookFullInfo){

    }

    public void hide(){
        constraintLayout.setVisibility(View.INVISIBLE);
        //Анимация
    }

    public void show(){
        constraintLayout.setVisibility(View.VISIBLE);
        //Анимация
    }

}
package ru.mirea.mobilefront.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.mirea.mobilefront.R;
import ru.mirea.mobilefront.service.BookService;

public class basket_fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (ViewGroup)inflater.inflate(R.layout.fragment_basket,container,false);

    }
}

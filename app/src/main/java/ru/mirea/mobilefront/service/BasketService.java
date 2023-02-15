package ru.mirea.mobilefront.service;

import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import ru.mirea.mobilefront.R;
import ru.mirea.mobilefront.dto.BookFull;

public class BasketService {
    @Getter
    private static final MutableLiveData<HashMap<BookFull, Integer>> basketBookList= new MutableLiveData<>();


    public static double getFinalCost() {
        HashMap<BookFull, Integer> basket = basketBookList.getValue();
        double finalCost = 0.0;
        for (Map.Entry<BookFull, Integer> entry : basket.entrySet()) {
            BookFull book = entry.getKey();
            int bookCount = entry.getValue();
            finalCost += book.getPrice() * bookCount;
        }
        return finalCost;
    }
}

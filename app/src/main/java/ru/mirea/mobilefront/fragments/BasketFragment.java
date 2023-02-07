package ru.mirea.mobilefront.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.HashMap;
import java.util.Map;

import ru.mirea.mobilefront.R;
import ru.mirea.mobilefront.adapter.BookVerticalBasketViewAdapter;
import ru.mirea.mobilefront.dto.BookFull;
import ru.mirea.mobilefront.service.BasketService;

public class BasketFragment extends Fragment {
    private MaterialButton paymentButton;
    private RecyclerView basketView;
    private ImageView backgroundTint;
    private TextView basketFinalPrice;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (ViewGroup)inflater.inflate(R.layout.fragment_basket,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        paymentButton = view.findViewById(R.id.procced_to_paymnt_button);

        basketFinalPrice = view.findViewById(R.id.final_basket_price);
        backgroundTint = view.findViewById(R.id.basket_tint_image);
        backgroundTint.setTranslationZ(-1f);
        basketView = view.findViewById(R.id.basket_books_view);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), RecyclerView.VERTICAL);
        Drawable d = ResourcesCompat.getDrawable(this.getResources(), R.drawable.divider_vertical_res, null);
        dividerItemDecoration.setDrawable(d);
        basketView.addItemDecoration(dividerItemDecoration);

        RecyclerView.LayoutManager layoutManager = new
                LinearLayoutManager(view.getContext()
                ,RecyclerView.VERTICAL
                ,false);
        basketView.setLayoutManager(layoutManager);

        BookVerticalBasketViewAdapter adapter = new BookVerticalBasketViewAdapter(view.getContext(), null);

        BasketService.getBasketBookList().observe(getViewLifecycleOwner(), new Observer<HashMap<BookFull, Integer>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(HashMap<BookFull, Integer> bookFulls) {
                adapter.setBookList(bookFulls);
                basketView.setAdapter(adapter);

                basketFinalPrice.setText("Итоговая стоимость вашей корзины: "+ BasketService.getFinalCost()+" руб");

            }
        });
        //Панель оплаты
        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!BasketService.getBasketBookList().getValue().isEmpty()){
                    PaymentDialogFragment paymentDialogFragment = PaymentDialogFragment.newInstance();
                    //fill dialog


                    paymentDialogFragment.show(getParentFragmentManager(), "bottom_payment");
                }
            }
        });
    }


}

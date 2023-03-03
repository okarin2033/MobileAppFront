package ru.mirea.mobilefront.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ru.mirea.mobilefront.R;
import ru.mirea.mobilefront.dto.BookFull;
import ru.mirea.mobilefront.dto.OrderDto;
import ru.mirea.mobilefront.service.BasketService;
import ru.mirea.mobilefront.service.OrderService;

public class PaymentDialogFragment extends BottomSheetDialogFragment {
    public static PaymentDialogFragment newInstance(){
        return new PaymentDialogFragment();
    }
    private LinearLayout finalBasket;
    private TextView finalPriceText;
    private Button paymentButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.payment_panel_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        HashMap<BookFull, Integer> basket = BasketService.getBasketBookList().getValue();
        paymentButton = view.findViewById(R.id.pay_button);
        finalBasket = view.findViewById(R.id.final_list_basket);
        finalPriceText = view.findViewById(R.id.final_price_text);
        for (Map.Entry<BookFull, Integer> entry : basket.entrySet()) {
            BookFull book = entry.getKey();
            Integer amount = entry.getValue();
            TextView textView = new TextView(view.getContext());
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setText(amount + " x " + book.getBookName() + " " + book.getPrice()+" ₽");
            textView.setTextColor(Color.BLACK);
            finalBasket.addView(textView);
            finalPriceText.setText(BasketService.getFinalCost()+" ₽");
        }

        paymentButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                HashMap<String, Integer> orderMap = new HashMap<>();
                Map<BookFull, Integer> basketMap = BasketService.getBasketBookList().getValue();
                basketMap.keySet().forEach(book -> orderMap.put(book.getBookName(), basketMap.get(book)));
                OrderDto orderDto = new OrderDto(orderMap, BasketService.getFinalCost(), new Date().toString());
                OrderService.sendOrder(orderDto);
                dismiss();
                { //сбрасываем корзину
                    BasketService.getBasketBookList().postValue(new HashMap<BookFull, Integer>());
                }
            }
        });
    }
}

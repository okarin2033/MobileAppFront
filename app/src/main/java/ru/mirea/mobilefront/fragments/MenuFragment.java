package ru.mirea.mobilefront.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import ru.mirea.mobilefront.R;
import ru.mirea.mobilefront.adapter.BookVerticalViewAdapter;
import ru.mirea.mobilefront.adapter.OrderViewAdapter;
import ru.mirea.mobilefront.dto.OrderDto;
import ru.mirea.mobilefront.service.OrderService;

public class MenuFragment extends Fragment {

    RecyclerView orderView;
    TextView finalText;
    @Getter
    static FragmentManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (ViewGroup)inflater.inflate(R.layout.fragment_order,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        finalText = view.findViewById(R.id.final_oder_text);
        orderView = view.findViewById(R.id.order_list_view);
        manager = getParentFragmentManager();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), RecyclerView.VERTICAL);
        Drawable d = ResourcesCompat.getDrawable(this.getResources(), R.drawable.divider_vertical_res, null);
        dividerItemDecoration.setDrawable(d);
        orderView.addItemDecoration(dividerItemDecoration);

        RecyclerView.LayoutManager layoutManager = new
                LinearLayoutManager(view.getContext()
                ,RecyclerView.VERTICAL
                ,false);
        orderView.setLayoutManager(layoutManager);
        //Заполнение списка
        MutableLiveData<List<OrderDto>> orderData= OrderService.getOrderData();
        orderData.observe(getViewLifecycleOwner(), new Observer<List<OrderDto>>() {
            @Override
            public void onChanged(List<OrderDto> orderDtos) {
                OrderViewAdapter adapter = new OrderViewAdapter(view.getContext(), orderDtos);
                orderView.setAdapter(adapter);
                finalText.setText("Всего вы сделали заказов на "+ OrderService.getOrdersSum()+" рублей. Спасибо!");
            }
        });

        //обновить данные заказов
        OrderService.getOrders();
    }
}
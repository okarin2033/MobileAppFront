package ru.mirea.mobilefront.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.mobilefront.R;
import ru.mirea.mobilefront.dto.OrderDto;
import ru.mirea.mobilefront.fragments.MenuFragment;
import ru.mirea.mobilefront.fragments.OrderDialogFragment;

public class OrderViewAdapter extends RecyclerView.Adapter<OrderViewAdapter.OrderViewHolder>{

    private List<OrderDto> orderDtoList;
    Context context;
    public OrderViewAdapter(Context context, List<OrderDto> list){
        this.orderDtoList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View orderItems = LayoutInflater.from(context).inflate(R.layout.order_panel, parent, false);
        return new OrderViewAdapter.OrderViewHolder(orderItems);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.dateText.setText("Дата заказа - "+orderDtoList.get(position).getDate());
        holder.statusText.setText("Статус заказа - обработан и оплачен");
        holder.summText.setText("Заказ на сумму "+ orderDtoList.get(position).getFullPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Заказ от"+orderDtoList.get(position).getDate());
                OrderDialogFragment orderDialogFragment = OrderDialogFragment.newInstance(position);
                orderDialogFragment.show(MenuFragment.getManager(), "bottom_order");
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderDtoList.size();
    }


    public static final class OrderViewHolder extends RecyclerView.ViewHolder{

        TextView dateText;
        TextView summText;
        TextView statusText;

        public OrderViewHolder(@NonNull View orderView) {
            super(orderView);
            dateText = orderView.findViewById(R.id.order_date_text);
            summText = orderView.findViewById(R.id.order_summ_text);
            statusText = orderView.findViewById(R.id.order_sta_text);
        }
    }
}

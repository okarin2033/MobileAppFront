package ru.mirea.mobilefront.design;

import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.mirea.mobilefront.MainActivity;
import ru.mirea.mobilefront.MenuActivity;
import ru.mirea.mobilefront.R;
import ru.mirea.mobilefront.dto.BookSimple;

public class BookViewAdapter extends RecyclerView.Adapter<BookViewAdapter.BookViewHolder> {

    Context context;
    List<BookSimple> bookList;
    Dialog dialog;

    public BookViewAdapter(Context context, List<BookSimple> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View categoryItems = LayoutInflater.from(context).inflate(R.layout.book_card, parent, false);
        return new BookViewHolder(categoryItems);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BookSimple book = bookList.get(position);
        holder.bookName.setText(book.getName());
        Picasso.get()
                .load(book.getImageUrl())
                .placeholder(R.drawable.book_100)
                .error(R.drawable.book_100)
                .into(holder.bookImage);
        // image from picasso
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookSrc = bookList.get(position).getUrl();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static final class BookViewHolder extends RecyclerView.ViewHolder{

        TextView bookName;
        ImageView bookImage;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            bookName = itemView.findViewById(R.id.book_card_name);
            bookImage = itemView.findViewById(R.id.book_card_image);


        }
    }

}

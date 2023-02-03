package ru.mirea.mobilefront.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import ru.mirea.mobilefront.R;
import ru.mirea.mobilefront.dto.BookFull;
import ru.mirea.mobilefront.dto.BookSimple;
import ru.mirea.mobilefront.service.BookService;

public class BookVerticalBasketViewAdapter extends RecyclerView.Adapter<BookVerticalBasketViewAdapter.BookViewHolder> {

    Context context;
    @Setter
    @Getter
    HashMap<BookFull, Integer> bookList;

    public BookVerticalBasketViewAdapter(Context context, HashMap<BookFull, Integer> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View categoryItems = LayoutInflater.from(context).inflate(R.layout.book_panel, parent, false);
        return new BookViewHolder(categoryItems);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ArrayList<BookFull> books= new ArrayList<>(bookList.keySet());
        BookFull book = books.get(position);
        holder.bookName.setText(book.getBookName());
        Picasso.get()
                .load(book.getImageUrl().get(0))
                .placeholder(R.drawable.book_100)
                .error(R.drawable.book_100)
                .into(holder.bookImage);
        holder.bookAuthor.setText(books.get(position).getAuthor());
        // image from picasso
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookSrc = books.get(position).getUrl();
                System.out.println(bookSrc);
                BookService.getFullBookData(bookSrc+"/");
            }
        });
    }

    @Override
    public int getItemCount() {
        if (bookList == null)
            return 0;
        return bookList.size();
    }

    public static final class BookViewHolder extends RecyclerView.ViewHolder{

        TextView bookName;
        ImageView bookImage;
        TextView bookAuthor;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            bookName = itemView.findViewById(R.id.book_panel_name);
            bookImage = itemView.findViewById(R.id.book_panel_image);
            bookAuthor = itemView.findViewById(R.id.book_panel_author);


        }
    }

}

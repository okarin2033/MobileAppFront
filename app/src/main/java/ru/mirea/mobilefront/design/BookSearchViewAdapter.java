package ru.mirea.mobilefront.design;

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

import ru.mirea.mobilefront.R;
import ru.mirea.mobilefront.dto.BookSimple;

public class BookSearchViewAdapter extends RecyclerView.Adapter<BookSearchViewAdapter.BookViewHolder> {

    Context context;
    List<BookSimple> bookList;

    public BookSearchViewAdapter(Context context, List<BookSimple> bookList) {
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
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BookSimple book = bookList.get(position);
        holder.bookName.setText(book.getName());
        Picasso.with(context)
                .load(book.getImageUrl())
                .placeholder(R.drawable.book_100)
                .error(R.drawable.book_100)
                .into(holder.bookImage);
        holder.bookAuthor.setText(bookList.get(position).getAuthor());
        // image from picasso
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

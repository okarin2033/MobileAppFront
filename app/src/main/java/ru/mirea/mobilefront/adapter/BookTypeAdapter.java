package ru.mirea.mobilefront.adapter;

import ru.mirea.mobilefront.dto.BookFull;
import ru.mirea.mobilefront.dto.BookSimple;

public class BookTypeAdapter {

    public static BookSimple getSimpleBook(BookFull bookFull){
        BookSimple book= new BookSimple();
        book.setAuthor(bookFull.getAuthor());
        book.setImageUrl(bookFull.getImageUrl().get(0));
        book.setName(bookFull.getBookName());
        book.setUrl(bookFull.getUrl());
        return book;
    }
}

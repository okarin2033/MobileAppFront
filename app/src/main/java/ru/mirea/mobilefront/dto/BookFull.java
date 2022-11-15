package ru.mirea.mobilefront.dto;

import lombok.Data;

@Data
public class BookFull {
    String bookName;
    String url;
    String imageUrl;
    String genre;
    String articul;
    String author;
    String description;
    int price;
}

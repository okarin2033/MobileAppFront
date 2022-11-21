package ru.mirea.mobilefront.dto;

import java.util.List;

import lombok.Data;

@Data
public class BookFull {
    String bookName;
    String url;
    List<String> imageUrl;
    String genre;
    String articul;
    String author;
    String description;
    double price;
}

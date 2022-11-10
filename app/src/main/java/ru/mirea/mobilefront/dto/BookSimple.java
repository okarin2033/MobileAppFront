package ru.mirea.mobilefront.dto;

import androidx.annotation.NonNull;

import lombok.Data;

@Data
public class BookSimple {
    String name;
    String imageUrl;
    String url;
    String author;

     @Override
    public String toString(){
         return name+" - "+author+"\n\n";
     }
}

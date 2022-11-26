package ru.mirea.mobilefront.dto;

import lombok.Data;
import java.util.Map;


@Data
public class OrderDto {
    private final Map<String, Double> bookList;
    private final double fullPrice;
}
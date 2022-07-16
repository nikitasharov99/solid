package ru.netology.filter;

import ru.netology.products.Product;

import java.util.Map;

public interface Filter {
    Map<Product, Integer> filter(Map<Product, Integer> products);
}

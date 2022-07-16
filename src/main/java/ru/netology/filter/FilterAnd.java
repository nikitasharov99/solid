package ru.netology.filter;

import ru.netology.products.Product;

import java.util.Map;

public class FilterAnd implements Filter {
    private final Filter firstFilter;
    private final Filter secondFilter;

    public FilterAnd(Filter firstFilter, Filter secondFilter) {
        this.firstFilter = firstFilter;
        this.secondFilter = secondFilter;
    }

    @Override
    public Map<Product, Integer> filter(Map<Product, Integer> products) {
        Map<Product, Integer> firstFilterProducts = firstFilter.filter(products);
        return secondFilter.filter(firstFilterProducts);
    }
}

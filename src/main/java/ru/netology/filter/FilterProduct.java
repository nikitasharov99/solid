package ru.netology.filter;

import ru.netology.products.Product;

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilterProduct implements Filter {
    Predicate<Product> predicate;

    public FilterProduct(Predicate<Product> predicate) {
        this.predicate = predicate;
    }

    @Override
    public Map<Product, Integer> filter(Map<Product, Integer> products) {
        return products.entrySet()
                .stream()
                .filter(entry -> predicate.test(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}

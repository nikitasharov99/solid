package ru.netology.filter;

import ru.netology.products.Product;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterOr implements Filter {
    private final Filter firstFilter;
    private final Filter secondFilter;

    public FilterOr(Filter firstFilter, Filter secondFilter) {
        this.firstFilter = firstFilter;
        this.secondFilter = secondFilter;
    }

    @Override
    public Map<Product, Integer> filter(Map<Product, Integer> products) {
        Map<Product, Integer> firstFilterProducts = firstFilter.filter(products);
        Map<Product, Integer> secondFilterProducts = secondFilter.filter(products);

        return Stream.of(firstFilterProducts, secondFilterProducts)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (x1, x2) -> x1
                ));
    }
}

package ru.netology;

import ru.netology.products.Product;
import ru.netology.products.Products;

public class Main {
    public static void main(String[] args) {
        Products products = Products.getInstance();
        products.addProduct(new Product(1234, "Яблоко", "Сады Ставрополья", 123.00), 17)
                .addProduct(new Product(8765, "Ананас", "Сады Приморья", 123.00), 21)
                .addProduct(new Product(2356, "Груша", "Сады Приморья", 124.00), 12)
                .addProduct(new Product(6789, "Арбуз", "Сады Ставрополья", 50.00), 10)
                .addProduct(new Product(3456, "Алыча", "Сады Приморья", 125.00), 11);

        UI.startShopping();
    }
}
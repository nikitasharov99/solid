package ru.netology.products;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Products {
    private static Products instance = null;

    private final Map<Product, Integer> products = new HashMap<>();

    private Products() {}

    public static Products getInstance() {
        if (instance == null) instance = new Products();
        initProducts();
        return instance;
    }

    public Map<Product, Integer> getAllProducts() {
        return products;
    }

    public Map<Product, Integer> getAvailableProducts() {
        return products.entrySet()
                .stream()
                .filter(x -> x.getValue() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public int getProductCount(Product product) {
        return products.getOrDefault(product, 0);
    }

    public void updatePrice(Product product) {
        double newPrice = product.getPrice();
        if (newPrice > 0 && getCurrentPrice(product) != newPrice) {
            int countProducts = products.remove(product);
            products.put(product, countProducts);
        }
    }

    public double getCurrentPrice(Product product) {
        if (products.containsKey(product)) {
            return products.keySet().stream().findFirst().get().getPrice();
        }
        return -1;
    }

    public Products addProduct(Product product, int count) {
        products.put(product, getProductCount(product) + count);
        updatePrice(product);
        return instance;
    }

    public Product findProduct(String name, String manufacturer) {
        return products.keySet()
                .stream()
                .filter(product -> product.getName().equals(name) && product.getManufacturer().equals(manufacturer))
                .findFirst()
                .orElse(null);
    }

    public boolean containsProduct(Product product) {
        return products.containsKey(product);
    }

    public boolean removeProduct(Product product, int count) {
        int productCount = getProductCount(product);
        if (count > productCount) return false;
        products.put(product, productCount - count);
        return true;
    }

    public Product getProductByProductID(int productId) {
        return products.keySet()
                .stream()
                .filter(x -> x.getProductID() == productId)
                .findFirst()
                .orElse(null);
    }

    public static void initProducts() {
        instance.addProduct(new Product(1234, "Яблоко", "Сады Ставрополья", 123.00), 17)
                .addProduct(new Product(8765, "Ананас", "Сады Приморья", 123.00), 21)
                .addProduct(new Product(2356, "Груша", "Сады Приморья", 124.00), 12)
                .addProduct(new Product(6789, "Арбуз", "Сады Ставрополья", 50.00), 10)
                .addProduct(new Product(3456, "Алыча", "Сады Приморья", 125.00), 11)
                .addProduct(new Product(2355, "Груша", "Сады Ставрополья", 200.0), 50)
                .addProduct(new Product(4678, "Сливы", "Сады Ставрополья", 59.00), 80);
    }
}

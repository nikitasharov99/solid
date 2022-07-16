package ru.netology.cart;

import ru.netology.products.Product;
import ru.netology.products.Products;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private static Cart instance = null;

    private Cart() {}

    public static Cart getInstance() {
        if (instance == null) instance = new Cart();
        return instance;
    }

    Products products = Products.getInstance();

    private final Map<Product, Integer> cartItems = new HashMap<>();

    public boolean addToCart(Product product, int count) {
        if (product == null || !products.containsProduct(product) || count > products.getProductCount(product)) {
            return false;
        }
        cartItems.put(product, cartItems.getOrDefault(product, 0) + count);
        products.removeProduct(product, count);
        return true;
    }

    public boolean removeFromCart(Product product, int count) {
        if (product == null || !cartItems.containsKey(product) || cartItems.get(product) < count) {
            return false;
        }
        products.addProduct(product, count);
        if (cartItems.get(product) == count) {
            cartItems.remove(product);
        } else {
            cartItems.put(product, cartItems.get(product) - count);
        }
        return true;
    }

    public boolean addProductToCartByPID(int pid, int count) {
        Product product = products.getProductByProductID(pid);
        return addToCart(product, count);
    }

    public boolean removeProductByPID(int pid, int count) {
        Product product = products.getProductByProductID(pid);
        return removeFromCart(product, count);
    }

    public void clean() {
        cartItems.keySet().forEach(product -> products.addProduct(product, cartItems.get(product)));
        cartItems.clear();
    }

    public void buy() {
        cartItems.clear();
    }

    public void printCart() {
        if (isEmpty()) {
            System.out.println("Корзина пуста");
        } else {
            cartItems.forEach((product, count) -> System.out.println(product + " " + count));
        }
    }

    public Product findProduct(String name, String manufacturer) {
        return cartItems.keySet()
                .stream()
                .filter(product -> product.getName().equals(name) && product.getManufacturer().equals(manufacturer))
                .findFirst()
                .orElse(null);
    }

    public boolean isEmpty() {
        return cartItems.isEmpty();
    }
}

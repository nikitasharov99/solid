package ru.netology.products;

import java.util.Objects;

public class Product {
    private final int productID;
    private final String name;
    private final String manufacturer;
    private final Double price;

    public Product(int productID, String name, String manufacturer, Double price) {
        this.productID = productID;
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
    }

    public int getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return name.equals(product.getName()) && manufacturer.equals(product.getManufacturer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID, name, manufacturer);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", name='" + name + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", price=" + price +
                '}';
    }
}

package ru.netology;

import ru.netology.cart.Cart;
import ru.netology.filter.Filter;
import ru.netology.filter.FilterProduct;
import ru.netology.products.Product;
import ru.netology.products.Products;

import java.util.Map;
import java.util.Scanner;

public class UI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Products products = Products.getInstance();
    private static final Cart cart = Cart.getInstance();

    public static void startShopping() {
        System.out.println("Добро пожаловать в наш магазин");
        while (true) {
            menu();
            System.out.print("-->  ");
            String answer = scanner.nextLine();
            switch (answer) {
                case "1":
                    System.out.println("Список доступных товаров для покупки:");
                    printProducts(products.getAvailableProducts());
                    break;
                case "2":
                    cart.printCart();
                    break;
                case "3":
                    menuAddToCart();
                    break;
                case "4":
                    menuRemoveFromCart();
                    break;
                case "5":
                    menuFilter();
                    break;
                case "6":
                    if (cart.isEmpty()) {
                        System.out.println("Отсутствуют товары в корзине");
                    } else {
                        cart.buy();
                        System.out.println("Товары куплены.");
                    }
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Вы ввели несуществующий пункт меню\n");
            }
            System.out.println();
        }
    }

    private static void menu() {
        System.out.println("Главное меню. Выберите действие:");
        System.out.println("1. Напечатать все доступные товары для покупок");
        System.out.println("2. Напечатать корзину");
        System.out.println("3. Добавить товары в корзину");
        System.out.println("4. Удалить товары из корзины");
        System.out.println("5. Отфильтровать список доступных товаров");
        System.out.println("6. Оформить покупку");
        System.out.println("0. Выход");
    }

    public static void menuAddToCart() {
        while (true) {
            System.out.println("Добавить товары в корзину. Выберите действие");
            System.out.println("1. Добавить товар по его имени и производителю");
            System.out.println("2. Добавить товар по его артикулу");
            System.out.print("0. Назад\n-->  ");
            String answer = scanner.nextLine();
            switch (answer) {
                case "1":
                    System.out.print("Введите название товара\n-->  ");
                    String name = scanner.nextLine();
                    System.out.print("Введите производителя товара\n-->  ");
                    String manufacturer = scanner.nextLine();
                    Product product = products.findProduct(name, manufacturer);
                    if (product == null) {
                        System.out.println("Товар не найден");
                    } else {
                        System.out.print("Введите кол-во товара\n-->  ");
                        int count = Integer.parseInt(scanner.nextLine());
                        if (!cart.addToCart(product, count)) {
                            System.out.println("Невозможно добавить товар в корзину (" +
                                    "отсутствует нужное количество)");
                        }
                    }
                    break;
                case "2":
                    System.out.print("Введите артикул и кол-во товара (через пробел)" +
                            " для добавления в корзину\n-->  ");
                    String input = scanner.nextLine();
                    String[] splitInput = input.split(" ");
                    int pid = Integer.parseInt(splitInput[0]);
                    int count = Integer.parseInt(splitInput[1]);
                    if (!cart.addProductToCartByPID(pid, count)) {
                        System.out.println("Невозможно добавить товар в корзину (" +
                                "отсутствует нужное количество)");
                    }
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Вы ввели несуществующий пункт меню\n");
            }
        }
    }

    public static void menuRemoveFromCart() {
        while (true) {
            System.out.println("Удалить товары из корзины. Выберите действие");
            System.out.println("1. Удалить товар по его имени и производителю");
            System.out.println("2. Удалить товар по его артикулу");
            System.out.println("3. Очистить корзину");
            System.out.print("0. Назад\n-->  ");
            String answer = scanner.nextLine();
            switch (answer) {
                case "1":
                    System.out.print("Введите название товара\n-->  ");
                    String name = scanner.nextLine();
                    System.out.print("Введите производителя товара\n-->  ");
                    String manufacturer = scanner.nextLine();
                    Product product = cart.findProduct(name, manufacturer);
                    if (product == null) {
                        System.out.println("Товар отсутствует в корзине");
                    } else {
                        System.out.print("Введите кол-во товара\n-->  ");
                        int count = Integer.parseInt(scanner.nextLine());
                        if (!cart.removeFromCart(product, count)) {
                            System.out.println("Невозможно убрать товар из корзины (" +
                                    "нет такого кол-ва товара)");
                        }
                    }
                    break;
                case "2":
                    System.out.print("Введите артикул и кол-во товара (через пробел)\n-->  ");
                    String input = scanner.nextLine();
                    String[] splitInput = input.split(" ");
                    int pid = Integer.parseInt(splitInput[0]);
                    int count = Integer.parseInt(splitInput[1]);
                    if (!cart.removeProductByPID(pid, count)) {
                        System.out.println("Невозможно убрать товар из корзины (" +
                                "нет такого кол-ва товара)");
                    }
                    break;
                case "3":
                    cart.clean();
                case "0":
                    return;
                default:
                    System.out.println("Вы ввели несуществующий пункт меню\n");
            }
        }
    }

    public static void menuFilter() {
        while (true) {
            System.out.println("Отфильтровать список доступных товаров. Выберите действие");
            System.out.println("1. Отфильтровать по наименованию товара");
            System.out.println("2. Отфильтровать по производителю товара");
            System.out.println("3. Отфильтровать по цене");
            System.out.print("0. Назад\n-->  ");
            String answer = scanner.nextLine();
            String input;
            switch (answer) {
                case "1":
                    System.out.print("Введите часть наименования товара для поиска\n--> ");
                    input = scanner.nextLine();
                    Filter filterName = new FilterProduct(x -> x.getName().contains(input));
                    printProducts(filterName.filter(products.getAvailableProducts()));
                    break;
                case "2":
                    System.out.print("Введите часть производителя товара для поиска\n--> ");
                    input = scanner.nextLine();
                    Filter filterManufacturer = new FilterProduct(x -> x.getManufacturer().contains(input));
                    printProducts(filterManufacturer.filter(products.getAvailableProducts()));
                    break;
                case "3":
                    System.out.print("Введите цену товара для поиска\n--> ");
                    input = scanner.nextLine();
                    Filter filterPrice = new FilterProduct(x -> x.getPrice() == Integer.parseInt(input));
                    printProducts(filterPrice.filter(products.getAvailableProducts()));
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Вы ввели несуществующий пункт меню\n");
            }
        }
    }

    public static void printProducts(Map<Product, Integer> products) {
        products.forEach((product, count) -> System.out.println(product + " Кол-во:" + count));
    }
}

package controller;

import dao.OrdersDAO;
import dao.ProductDAO;
import domain.OrderItems;
import domain.Orders;
import domain.Products;
import domain.ProductsStatus;
import service.OrdersService;
import service.ProductsService;
import utils.ConnectionUtils;
import utils.UserIdGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    OrdersService ordersService;
    ProductsService productsService;

    public Controller() {
        ConnectionUtils connectionUtils = new ConnectionUtils();
        this.ordersService = new OrdersService(new OrdersDAO(connectionUtils, new UserIdGenerator()));
        this.productsService = new ProductsService(new ProductDAO(connectionUtils));
    }

    public void readConsole() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Выберите действие или нажмите '0' для выхода: ");
        System.out.println("1 - продукты");
        System.out.println("2 - заказы");
        System.out.println("3 - Views");
        String input = reader.readLine();
        while (!"0".equals(input)) {
            switch (input) {
                case "1":
                    products();
                    break;
                case "2":
                    orders();
                    break;
                case "3":
                    showViews(reader);
                    break;
                default:
                    throw new IllegalArgumentException("Неизвестный выбор!");
            }
            System.out.println("Выберите действие или нажмите '0' для выхода: ");
            System.out.println("1 - продукты");
            System.out.println("2 - заказы");
            System.out.println("3 - Views");
            input = reader.readLine();
        }
    }

    public void products() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Выберите действие или нажмите '0' для выхода: ");
        System.out.println("1 - создание продукта");
        System.out.println("2 - просмотр продуктов");
        System.out.println("3 - удаление продукта по id");
        System.out.println("4 - удаление всех продуктов");
        String input = reader.readLine();
        while (!"0".equals(input)) {
            switch (input) {
                case "1":
                    createProducts(reader);
                    break;
                case "2":
                    readProducts();
                    break;
                case "3":
                    deleteProductById(reader);
                    break;
                case "4":
                    deleteProducts(reader);
                    break;
                default:
                    throw new IllegalArgumentException("Неизвестный выбор!");
            }
            System.out.println("Выберите действие или нажмите '0' для выхода: ");
            System.out.println("1 - создание продукта");
            System.out.println("2 - просмотр продуктов");
            System.out.println("3 - удаление продукта по id");
            System.out.println("4 - удаление всех продуктов");
            input = reader.readLine();
        }
    }

    public void orders() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Выберите действие или нажмите '0' для выхода: ");
        System.out.println("1 - создание заказа");
        System.out.println("2 - просмотр заказов");
        System.out.println("3 - просмотр order_items");
        System.out.println("4 - обновление order_items");
        String input = reader.readLine();
        while (!"0".equals(input)) {
            switch (input) {
                case "1":
                    createOrders(reader);
                    break;
                case "2":
                    readOrders();
                    break;
                case "3":
                    readOrdersItems();
                    break;
                case "4":
                    updateOrdersItems(reader);
                    break;
                default:
                    throw new IllegalArgumentException("Неизвестный выбор!");
            }
            System.out.println("Выберите действие или нажмите '0' для выхода: ");
            System.out.println("1 - создание заказа");
            System.out.println("2 - просмотр заказов");
            System.out.println("3 - просмотр order_items");
            System.out.println("4 - обновление order_items");
            input = reader.readLine();
        }
    }

    private void showViews(BufferedReader reader) throws IOException {
        System.out.println("Выберите действие или нажмите '0' для выхода: ");
        System.out.println("1 - Product Name | Product Price | Product Status | for all products");
        System.out.println("2 - List all products, which have been ordered at least once, " +
            "with total ordered quantity sorted descending by the quantity");
        System.out.println("3 - | Order ID | Products total Price | Product Name | Products Quantity in orderEntry " +
            "| Order Created Date [YYYY-MM-DD HH:MM ] | by order Id");
        System.out.println("4 - List all orders using previous view");
        String input = reader.readLine();
        while (!"0".equals(input)) {
            switch (input) {
                case "1":
                    readProductsView();
                    break;
                case "2":
                    listProductsView();
                    break;
                case "3":
                    listOrdersByIdView(reader);
                    break;
                case "4":
                    listOrdersView();
                    break;
                default:
                    throw new IllegalArgumentException("Неизвестный выбор!");
            }
            System.out.println("Выберите действие или нажмите '0' для выхода: ");
            System.out.println("1 - Product Name | Product Price | Product Status | for all products");
            System.out.println("2 - List all products, which have been ordered at least once, " +
                "with total ordered quantity sorted descending by the quantity");
            System.out.println("3 - | Order ID | Products total Price | Product Name | Products Quantity in orderEntry " +
                "| Order Created Date [YYYY-MM-DD HH:MM ] | by order Id");
            System.out.println("4 - List all orders using previous view");
            input = reader.readLine();
        }
    }

    public void createProducts(BufferedReader reader) {
        Products products = new Products();
        try {
            System.out.println("Введите name:");
            String name = reader.readLine();
            products.setName(name);
            System.out.println("Введите price:");
            int price = Integer.parseInt(reader.readLine());
            products.setPrice(price);
            System.out.println("Введите status:");
            System.out.println("1 - out_of_stock");
            System.out.println("2 - in_stock");
            System.out.println("3 - running_low");
            String input = reader.readLine();
            switch (input) {
                case "1":
                    ProductsStatus status = ProductsStatus.out_of_stock;
                    products.setStatus(status);
                    break;
                case "2":
                    status = ProductsStatus.in_stock;
                    products.setStatus(status);
                    break;
                case "3":
                    status = ProductsStatus.running_low;
                    products.setStatus(status);
                    break;
            }
            productsService.createProducts(products);
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void readProducts() {
        try {
            productsService.selectProducts();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void deleteProducts(BufferedReader reader) {
        try {
            System.out.println("Введите пароль:");
            String password = reader.readLine();
            if (password.equals("qwerty"))
                productsService.deleteProducts();
            else System.out.println("Неверный пароль!");
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    private void deleteProductById(BufferedReader reader) {
        try {
            System.out.println("Введите product_id:");
            int id = Integer.parseInt(reader.readLine());
            productsService.deleteProductById(id);
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createOrders(BufferedReader reader) {
        Orders orders = new Orders();
        try {
            System.out.println("Введите products id's через пробел:");
            List<Integer> array;
            array = Arrays.stream(reader.readLine()
                .strip()
                .split(" ")).map(Integer::parseInt).collect(Collectors.toList());
            ordersService.createOrders(orders, array);
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    public void readOrders() {
        try {
            ordersService.selectOrders();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void readOrdersItems() {
        try {
            ordersService.selectOrdersItems();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateOrdersItems(BufferedReader reader) {
        OrderItems orderItems = new OrderItems();
        System.out.println("Введите order_id:");
        try {
            int id = Integer.parseInt(reader.readLine());
            System.out.println("Введите product_id:");
            int productId = Integer.parseInt(reader.readLine());
            System.out.println("Введите quantity:");
            int quantity = Integer.parseInt(reader.readLine());
            orderItems.setQuantity(quantity);
            ordersService.updateOrderItems(orderItems, id, productId);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void readProductsView() {
        try {
            productsService.selectProductsView();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void listProductsView() {
        try {
            ordersService.listProductsView();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void listOrdersView() {
        try {
            ordersService.listOrdersView();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void listOrdersByIdView(BufferedReader reader) {
        try {
            System.out.println("Введите order_id:");
            int id = Integer.parseInt(reader.readLine());
            ordersService.listOrdedersByIdView(id);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}

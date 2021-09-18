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
import java.sql.Date;
import java.sql.SQLException;
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
       // System.out.println("Добро пожаловать в интернет-магазин!");
        System.out.println("Выберите действие или нажмите '0' для выхода: ");
        System.out.println("1 - создание продукта");
        System.out.println("2 - просмотр продуктов");
        System.out.println("3 - обновление продукта");
        System.out.println("4 - удаление продукта");
        System.out.println("5 - создание заказа");
        System.out.println("6 - просмотр заказов");
        System.out.println("7 - просмотр order_items");
        System.out.println("8 - обновление заказа");
        System.out.println("9 - обновление order_items");
        System.out.println("10 - удаление заказа");
        System.out.println("11 - Views");
        System.out.println("12 - удаление всех продуктов");
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
                    updateProducts(reader);
                    break;
                case "4":
                    deleteProductById(reader);
                    break;
                case "5":
                    createOrders(reader);
                    break;
                case "6":
                    readOrders();
                    break;
                case "7":
                    readOrdersItems();
                    break;
                case "8":
                    updateOrders(reader);
                    break;
                case "9":
                    updateOrdersItems(reader);
                    break;
                case "10":
                    deleteOrders();
                case "11":
                    showViews();
                    break;
                case "12":
                    deleteProducts(reader);
                    break;
                default:
                    throw new IllegalArgumentException("Неизвестный выбор!");
            }
            System.out.println("Выберите действие или нажмите '0' для выхода: ");
            System.out.println("1 - создание продукта");
            System.out.println("2 - просмотр продуктов");
            System.out.println("3 - обновление продукта");
            System.out.println("4 - удаление продукта");
            System.out.println("5 - создание заказа");
            System.out.println("6 - просмотр заказов");
            System.out.println("7 - просмотр order_items");
            System.out.println("8 - обновление заказа");
            System.out.println("9 - обновление order_items");
            System.out.println("10 - удаление заказа");
            System.out.println("11 - Views");
            System.out.println("12 - удаление всех продуктов");
            input = reader.readLine();
        }
    }

    public void createProducts(BufferedReader reader) {
        Products products = new Products();
        System.out.println("Введите id:");
        try {
            int id = Integer.parseInt(reader.readLine());
            products.setId(id);
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

    public void readProductsView() {
        try {
            productsService.selectProductsView();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void readProducts() {
        try {
            productsService.selectProducts();
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

    public void updateProducts(BufferedReader reader) {
        Orders orders = new Orders();
        System.out.println("Введите новый status:");
        try {
            String status = reader.readLine();
            orders.setStatus(status);
            ordersService.updateOrders(orders);
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
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
        }
    }

    public void createOrders(BufferedReader reader) {
        Orders orders = new Orders();
        System.out.println("Введите id:");
        try {
            int id = Integer.parseInt(reader.readLine());
            orders.setId(id);
            System.out.println("Введите products id's");
            List<Integer> array;
            array = Arrays.stream(reader.readLine().strip().split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
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

    public void updateOrders(BufferedReader reader) {
        Orders orders = new Orders();
        System.out.println("Введите новый status:");
        try {
            String status = reader.readLine();
            orders.setStatus(status);
            ordersService.updateOrders(orders);
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateOrdersItems(BufferedReader reader) {
        OrderItems orderItems = new OrderItems();
        System.out.println("Введите order_id:");
        try {
            int id = Integer.parseInt(reader.readLine());
           // orderItems.setOrder_id(id);
            System.out.println("Введите product_id:");
            int productId = Integer.parseInt(reader.readLine());
          //  orderItems.setProduct_id(productId);
            System.out.println("Введите quantity:");
            int quantity = Integer.parseInt(reader.readLine());
            orderItems.setQuantity(quantity);
            ordersService.updateOrderItems(orderItems, id, productId);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrders() {
        try {
            ordersService.deleteOrders();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void showViews() {
        readProductsView();
        listProductsView();
    }
}

package controller;

import domain.Orders;
import domain.Products;
import domain.ProductsStatus;
import service.OrdersService;
import service.ProductsService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.SQLException;

public class Controller {
    OrdersService ordersService = new OrdersService();
    ProductsService productsService = new ProductsService();

    public void readConsole() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Добро пожаловать в интернет-магазин!");
        System.out.println("Выберите действие или нажмите '0' для выхода: ");
        System.out.println("1 - занесение информации в магазин");
        System.out.println("2 - просмотр информации");
        System.out.println("3 - обновление информации");
        System.out.println("4 - удаление информации");
        String input = reader.readLine();
        while (!"0".equals(input)) {
            switch (input) {
                case "1":
                   // createOrders(reader);
                    createProducts(reader);
                    break;
                case "2":
                   // readOrders();
                    readProducts();
                   // readProductsView();
                    break;
                case "3":
                    updateOrders(reader);
                    break;
                case "4":
                    deleteOrders();
                    break;
                default:
                    throw new IllegalArgumentException("Неизвестный выбор!");
            }
            System.out.println("Выберите действие или нажмите '0' для выхода: ");
            System.out.println("1 - занесение информации в магазин");
            System.out.println("2 - просмотр информации");
            System.out.println("3 - обновление информации");
            System.out.println("4 - удаление информации");
            input = reader.readLine();
        }
    }

    public void createOrders(BufferedReader reader) {
        Orders orders = new Orders();
        System.out.println("Введите id:");
        try {
            int id = Integer.parseInt(reader.readLine());
            orders.setId(id);
            System.out.println("Введите user_id:");
            int user_id = Integer.parseInt(reader.readLine());
            orders.setUser_id(user_id);
            System.out.println("Введите status:");
            String status = reader.readLine();
            orders.setStatus(status);
            System.out.println("Введите created_at:");
            String created_at = reader.readLine();
            orders.setCreated_at(created_at);
            ordersService.createOrders(orders);
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

    public void deleteOrders() {
        ordersService.deleteOrders();
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
           /* ProductsStatus status = ProductsStatus.valueOf(reader.readLine());
            products.setStatus(status);*/
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
            System.out.println("Введите created_at:");
            Date created_at = Date.valueOf(reader.readLine());
            products.setCreated_at(created_at);
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
}

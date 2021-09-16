package service;

import dao.OrdersDAO;
import domain.Orders;
import utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class OrdersService {
   // private final OrdersDAO ordersDAO = new OrdersDAO();

    ConnectionUtils connectionUtils = new ConnectionUtils();
    public void createOrders(Orders orders, List<Integer> productIds) throws SQLException {
        try (Connection connection = connectionUtils.getConnection()) {
            OrdersDAO ordersDAO = new OrdersDAO(connection);
          /*  Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/db";
            String login = "root";
            String password = "";
            try (Connection connection = DriverManager.getConnection(url, login, password)) {
                ordersDAO.createOrders(connection, orders);
            }*/
            ordersDAO.createOrders(connection, orders, productIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectOrders() throws SQLException {
       /* try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/db";
            String login = "root";
            String password = "";
            try (Connection connection = DriverManager.getConnection(url, login, password)) {
                ordersDAO.selectOrders(connection);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        try (Connection connection = connectionUtils.getConnection()) {
            OrdersDAO ordersDAO = new OrdersDAO(connection);
            ordersDAO.selectOrders(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteOrders() {
        /*try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/db";
            String login = "root";
           // String password = "";
            String password = "qwerty";
            try (Connection connection = DriverManager.getConnection(url, login, password)) {
                ordersDAO.removeOrders(connection);
            }
        } catch (ClassNotFoundException | SQLException e) {
           // e.printStackTrace();
            System.out.println(e.getMessage());
        }*/
        try (Connection connection = connectionUtils.getConnection()) {
            OrdersDAO ordersDAO = new OrdersDAO(connection);
            ordersDAO.removeOrders(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateOrders(Orders orders) throws SQLException {
       /* try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/db";
            String login = "root";
            String password = "";
            try (Connection connection = DriverManager.getConnection(url, login, password)) {
                ordersDAO.updateOrders(connection, orders);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try (Connection connection = connectionUtils.getConnection()) {
            OrdersDAO ordersDAO = new OrdersDAO(connection);
            ordersDAO.updateOrders(connection, orders);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

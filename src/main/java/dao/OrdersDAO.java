package dao;

import domain.OrderItems;
import domain.OrderStatus;
import domain.Orders;
import utils.ConnectionUtils;
import utils.UserIdGenerator;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class OrdersDAO {
    private final UserIdGenerator userIdGenerator;
    private final ConnectionUtils connectionUtils;

    public OrdersDAO(ConnectionUtils connectionUtils, UserIdGenerator userIdGenerator) {
        this.connectionUtils = connectionUtils;
        this.userIdGenerator = userIdGenerator;
    }

    public void createOrders(Orders orders, List<Integer> productsIds) throws SQLException {
        try (Connection connection = connectionUtils.getConnection()) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = new Date(System.currentTimeMillis());
            String currentDate = formatter.format(date);
            String sqlQuery = "insert into orders (id, user_id, status, created_at) values (?,?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
                statement.setInt(1, orders.getId());
                statement.setInt(2, userIdGenerator.generate());
                statement.setString(3, OrderStatus.CREATED.name());
                statement.setString(4, currentDate);
                statement.executeUpdate();
            }
            insertOrderItems(connection, orders.getId(), productsIds);
        }
    }

    private void insertOrderItems(Connection connection, int orderId, List<Integer> ids) throws SQLException {
        for (int productId: ids) {
            String sqlQuery = "insert into order_items (order_id, product_id) values (?,?)";
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
                statement.setInt(1, orderId);
                statement.setInt(2, productId);
                statement.executeUpdate();
            }
        }
    }

    public void selectOrders() throws SQLException {
        try (Connection connection = connectionUtils.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM orders");
            System.out.println("+----+---------+--------+------------+");
            System.out.println("| id | user_id | status | created_at |");
            System.out.println("+----+---------+--------+------------+");
            while (resultSet.next()) {
                System.out.printf("| %2s | %7s | %6s | %10s |%n",
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4));
            }
            System.out.println("+----+---------+--------+------------+");
            resultSet.close();
            statement.close();
        }
    }

    public void selectOrdersItems() throws SQLException {
        try (Connection connection = connectionUtils.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM order_items");
            System.out.println("+----------+------------+----------+");
            System.out.println("| order_id | product_id | quantity |");
            System.out.println("+----------+------------+----------+");
            while (resultSet.next()) {
                System.out.printf("| %7s | %7s | %6s | %n",
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3));
            }
            System.out.println("+----------+------------+----------+");
            resultSet.close();
            statement.close();
        }
    }

    public void updateOrders(Orders orders) throws SQLException {
        try (Connection connection = connectionUtils.getConnection()) {
            String sqlQuery = "update orders set status = ?";
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
                statement.setString(1, orders.getStatus());
                statement.executeUpdate();
            }
        }
    }

    public void updateOrdersItems(OrderItems orderItems, int orderId, int productId) throws SQLException {
        try (Connection connection = connectionUtils.getConnection()) {
            String sqlQuery = "update order_items set quantity = ? where order_id = ? and product_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
                statement.setInt(1, orderItems.getQuantity());
                statement.setInt(2, orderId);
                statement.setInt(3, productId);
                statement.executeUpdate();
            }
        }
    }

    public void removeOrders() throws SQLException {
        try (Connection connection = connectionUtils.getConnection()) {
            String sqlQuery = "Delete FROM orders";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.executeUpdate(sqlQuery);
            statement.close();
        }
    }

    public void listProductsView() throws SQLException {
        try (Connection connection = connectionUtils.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select name, sum(quantity) from products, order_items " +
                "where products.id =order_items.product_id and quantity > 0 group by name order by sum(quantity) desc;");
            System.out.println("+------+---------------+");
            System.out.println("| name | sum(quantity) |");
            System.out.println("+------+---------------+");
            while (resultSet.next()) {
                System.out.printf("| %5s | %10s | %n",
                    resultSet.getString(1),
                    resultSet.getString(2));
            }
            System.out.println("+----------+------------+----------+");
            resultSet.close();
            statement.close();
        }
    }
}

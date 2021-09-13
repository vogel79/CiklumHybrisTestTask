package dao;

import domain.Orders;

import java.sql.*;

public class OrdersDAO {
    private final Connection connection;

    public OrdersDAO(Connection connection) {
        this.connection = connection;
    }

    public void createOrders(Connection connection, Orders orders) throws SQLException {
        String sqlQuery = "insert into orders (id, user_id, status, created_at) values (?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setInt(1, orders.getId());
            statement.setInt(2, orders.getUser_id());
            statement.setString(3, orders.getStatus());
            statement.setString(4, orders.getCreated_at());
            statement.executeUpdate();
        }
    }

    public void selectOrders(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM orders");
        System.out.println("+----+---------+--------+------------+");
        System.out.println("| id | user_id | status | created_at |");
        System.out.println("+----+---------+--------+------------+");
        while (resultSet.next()) {
           /* String str = resultSet.getString("id")
                + ":" + resultSet.getString(2)
                + ":" + resultSet.getString(3)
                + ":" + resultSet.getString(4);
            System.out.println("Заказ:" + str);*/
            String str = "|\t" + resultSet.getString("id")
                + "|\t\t  " + resultSet.getString(2)
                + "|\t   " + resultSet.getString(3)
                + "|\t\t\t" + resultSet.getString(4) + "|";
            System.out.println(str);
        }
        System.out.println("+----+---------+--------+------------+");
        resultSet.close();
        statement.close();
    }

    public void updateOrders(Connection connection, Orders orders) throws SQLException {
        String sqlQuery = "update orders set status = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, orders.getStatus());
            statement.executeUpdate();
        }
    }

    public void removeOrders(Connection connection) throws SQLException {
        String sqlQuery = "Delete FROM orders";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        statement.executeUpdate(sqlQuery);
        statement.close();
    }
}

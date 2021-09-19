package dao;

import domain.Products;
import utils.ConnectionUtils;

import java.sql.*;
import java.text.SimpleDateFormat;

public class ProductDAO {
    private final ConnectionUtils connectionUtils;

    public ProductDAO(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    public void createProduct(Products products) throws SQLException {
        try (Connection connection = connectionUtils.getConnection()) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = new Date(System.currentTimeMillis());
            // System.out.println(date);
            String currentDate = formatter.format(date);
            //  System.out.println(currentDate);
            String sqlQuery = "insert into products (id, name, price, status, created_at) values (?,?,?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
                statement.setInt(1, products.getId());
                statement.setString(2, products.getName());
                statement.setInt(3, products.getPrice());
                statement.setString(4, products.getStatus().name());
                statement.setString(5, currentDate);
                statement.executeUpdate();
            }
        }
    }

    public void selectProducts() throws SQLException {
        try (Connection connection = connectionUtils.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM products");
            System.out.println("+-----+--------+-------+--------------+------------+");
            System.out.println("| id  |  name  | price |    status    | created_at |");
            System.out.println("+-----+--------+-------+--------------+------------+");
            while (resultSet.next()) {
                System.out.printf("| %3s | %6s | %5s | %12s | %9s |%n",
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5));
            }
            System.out.println("+-----+--------+-------+--------------+------------+");
            resultSet.close();
            statement.close();
        }
    }

    public void selectProductsView() throws SQLException {
        try (Connection connection = connectionUtils.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name 'Product Name', " +
                "price 'Product Price', status 'Product Status' FROM products");
            System.out.println("+--------------+---------------+----------------+");
            System.out.println("| Product Name | Product Price | Product Status |");
            System.out.println("+--------------+---------------+----------------+");
            while (resultSet.next()) {
                System.out.printf("| %12s | %13s | %14s |%n",
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3));
            }
            System.out.println("+--------------+---------------+----------------+");
            resultSet.close();
            statement.close();
        }
    }

    public void removeProducts() throws SQLException {
        try (Connection connection = connectionUtils.getConnection()) {
            String sqlQuery = "Delete FROM products";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.executeUpdate(sqlQuery);
            statement.close();
        }
    }

    public void removeProductsById(int id) throws SQLException {
        try (Connection connection = connectionUtils.getConnection()) {
            String sqlQuery = "Delete FROM products where id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        }
    }
}

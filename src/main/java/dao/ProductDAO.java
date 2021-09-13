package dao;

import domain.Products;

import java.sql.*;

public class ProductDAO {
    private final Connection connection;

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    public void createProduct(Connection connection, Products products) throws SQLException {
             String sqlQuery = "insert into products (id, name, price, status, created_at) values (?,?,?,?,?)";
       // String sqlQuery = "insert into products (id, name, price, created_at) values (?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setInt(1, products.getId());
            statement.setString(2, products.getName());
            statement.setInt(3, products.getPrice());
            statement.setObject(4, products.getStatus());
            statement.setDate(5, products.getCreated_at());
            statement.executeUpdate();
        }
    }

    public void selectProducts(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM products");
        System.out.println("+----+------+-------+--------+------------+");
        System.out.println("| id | name | price | status | created_at |");
        System.out.println("+----+------+-------+--------+------------+");
        while (resultSet.next()) {
            String str = "|\t" + resultSet.getString("id")
                + "|\t" + resultSet.getString(2)
                + "|\t   " + resultSet.getString(3)
                + "|\t " + resultSet.getString(4)
                + "|\t" + resultSet.getString(5) + "|";
            System.out.println(str);
        }
        System.out.println("+----+------+-------+--------+------------+");
        resultSet.close();
        statement.close();
    }
}

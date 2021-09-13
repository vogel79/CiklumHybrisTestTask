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
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setInt(1, products.getId());
            statement.setString(2, products.getName());
            statement.setInt(3, products.getPrice());
           // statement.setObject(4, products.getStatus());
            statement.setString(4, products.getStatus().name());
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
            /*String str = "|\t" + resultSet.getString("id")
                + "|\t" + resultSet.getString(2)
                + "|\t   " + resultSet.getString(3)
                + "|\t " + resultSet.getString(4)
                + "|\t" + resultSet.getString(5) + "|";
            System.out.println(str);*/
            System.out.printf("| %2s | %4s | %5s | %9s | %9s |%n",
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5));
        }
        System.out.println("+----+------+-------+--------+------------+");
        resultSet.close();
        statement.close();
    }

    public void selectProductsView(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT name 'Product Name', " +
            "price 'Product Price', status 'Product Status' FROM products");
        System.out.println("+--------------+---------------+----------------+");
        System.out.println("| Product Name | Product Price | Product Status |");
        System.out.println("+--------------+---------------+----------------+");
        while (resultSet.next()) {
           /* String str = "|\t\t\t" + resultSet.getString(1)
                + "|\t\t\t  " + resultSet.getString(2)
                + "|\t\t\t" + resultSet.getString(3) + "|";
            System.out.println(str);
            */
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

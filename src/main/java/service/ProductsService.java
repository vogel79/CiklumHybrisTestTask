package service;

import dao.OrdersDAO;
import dao.ProductDAO;
import domain.Orders;
import domain.Products;
import utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class ProductsService {
    ConnectionUtils connectionUtils = new ConnectionUtils();
    public void createProducts(Products products) throws SQLException {
        try (Connection connection = connectionUtils.getConnection()) {
            ProductDAO productDAO = new ProductDAO(connection);
            productDAO.createProduct(connection, products);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectProducts() throws SQLException {
        try (Connection connection = connectionUtils.getConnection()) {
            ProductDAO productsDAO = new ProductDAO(connection);
            productsDAO.selectProducts(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteOrders() {
        try (Connection connection = connectionUtils.getConnection()) {
            OrdersDAO ordersDAO = new OrdersDAO(connection);
            ordersDAO.removeOrders(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateOrders(Orders orders) throws SQLException {
        try (Connection connection = connectionUtils.getConnection()) {
            OrdersDAO ordersDAO = new OrdersDAO(connection);
            ordersDAO.updateOrders(connection, orders);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

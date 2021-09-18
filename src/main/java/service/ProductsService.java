package service;

import dao.ProductDAO;
import domain.Products;
import java.sql.SQLException;

public class ProductsService {
    private final ProductDAO productDAO;

    public ProductsService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public void createProducts(Products products) throws SQLException {
        productDAO.createProduct(products);
    }

    public void selectProducts() throws SQLException {
        productDAO.selectProducts();
    }

    public void selectProductsView() throws SQLException {
        productDAO.selectProductsView();
    }

    public void deleteProducts() throws SQLException {
        productDAO.removeProducts();
    }

    public void deleteProductById(int id) throws SQLException {
        productDAO.removeProductsById(id);
    }

    /* public void updateProducts(Orders orders) throws SQLException {
        try (Connection connection = connectionUtils.getConnection()) {
            ProductsDAO ordersDAO = new OrdersDAO(connection);
            ordersDAO.updateOrders(connection, orders);
        }
    }*/
}

package service;

import dao.OrdersDAO;
import domain.OrderItems;
import domain.Orders;

import java.sql.SQLException;
import java.util.List;

public class OrdersService {

    private final OrdersDAO ordersDAO;

    public OrdersService(OrdersDAO ordersDAO) {
        this.ordersDAO = ordersDAO;
    }

    public void createOrders(Orders orders, List<Integer> productIds) throws SQLException {
        ordersDAO.createOrders(orders, productIds);
    }

    public void selectOrders() throws SQLException {
        ordersDAO.selectOrders();
    }

    public void selectOrdersItems() throws SQLException {
        ordersDAO.selectOrdersItems();
    }

    public void updateOrderItems(OrderItems orderItems, int orderId, int productId) throws SQLException {
        ordersDAO.updateOrdersItems(orderItems, orderId, productId);
    }

    public void listOrdersView() throws SQLException {
        ordersDAO.listOrdersView();
    }

    public void listOrdedersByIdView(int id) throws SQLException {
        ordersDAO.listOrdersByIdView(id);
    }

    public void listProductsView() throws SQLException {
        ordersDAO.listProductsView();
    }
}

package DAO;
import DTO.OrderDTO;
import DTO.OrderItemDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDAO {

    public void addOrder(OrderDTO order) throws SQLException {
        Connection conn = null;
        PreparedStatement orderStmt = null;
        PreparedStatement itemStmt = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);


            String orderQuery = "INSERT INTO orders (customer_id, order_date) VALUES (?, ?)";
            orderStmt = conn.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, order.getCustomerId());
            orderStmt.setTimestamp(2, new Timestamp(order.getOrderDate().getTime()));
            orderStmt.executeUpdate();


            ResultSet rs = orderStmt.getGeneratedKeys();
            if (rs.next()) {
                int orderId = rs.getInt(1);
                order.setOrderId(orderId);


                String itemQuery = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
                itemStmt = conn.prepareStatement(itemQuery);
                for (OrderItemDTO item : order.getOrderItems()) {
                    itemStmt.setInt(1, orderId);
                    itemStmt.setInt(2, item.getProductId());
                    itemStmt.setInt(3, item.getQuantity());
                    itemStmt.setDouble(4, item.getPrice());
                    itemStmt.executeUpdate();
                }
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (orderStmt != null) orderStmt.close();
            if (itemStmt != null) itemStmt.close();
            if (conn != null) conn.close();
        }
    }


    public List<OrderDTO> getOrdersByCustomerId(int customerId) throws SQLException {
        List<OrderDTO> orders = new ArrayList<>();
        String orderQuery = "SELECT * FROM orders WHERE customer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(orderQuery)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                List<OrderItemDTO> items = getOrderItemsByOrderId(orderId);
                OrderDTO order = new OrderDTO(orderId, customerId, rs.getTimestamp("order_date"), items);
                orders.add(order);
            }
        }
        return orders;
    }

    private List<OrderItemDTO> getOrderItemsByOrderId(int orderId) throws SQLException {
        List<OrderItemDTO> items = new ArrayList<>();
        String query = "SELECT * FROM order_items WHERE order_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                OrderItemDTO item = new OrderItemDTO(
                        rs.getInt("order_item_id"),
                        orderId,
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                );
                items.add(item);
            }
        }
        return items;
    }
}
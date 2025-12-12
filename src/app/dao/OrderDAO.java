// src/main/java/app/dao/OrderDAO.java
package app.dao;

import java.sql.*;

public class OrderDAO {
    public int createOrder(Connection c, int userId, double total) throws SQLException {
        String sql = "INSERT INTO orders(user_id, status, total, created_at) VALUES(?, 'PENDING', ?, CURRENT_TIMESTAMP)";
        try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.setDouble(2, total);
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) return keys.getInt(1);
            throw new SQLException("No order id");
        }
    }

    public void addItem(Connection c, int orderId, int productId, int qty, double unitPrice) throws SQLException {
        String sql = "INSERT INTO order_items(order_id, product_id, quantity, unit_price) VALUES(?,?,?,?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            ps.setInt(3, qty);
            ps.setDouble(4, unitPrice);
            ps.executeUpdate();
        }
    }

    public boolean decreaseStock(Connection c, int productId, int qty) throws SQLException {
        String sql = "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, qty);
            ps.setInt(2, productId);
            ps.setInt(3, qty);
            return ps.executeUpdate() > 0;
        }
    }
}
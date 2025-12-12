// src/main/java/app/service/OrderService.java
package app.service;

import app.config.Database;
import app.dao.OrderDAO;
import app.model.Product;

import java.sql.Connection;
import java.util.Map;

public class OrderService {
    private final OrderDAO orderDAO = new OrderDAO();

    public boolean checkout(int userId, Map<Product, Integer> items) {
        double total = items.entrySet().stream()
                .mapToDouble(e -> e.getKey().getPrice() * e.getValue())
                .sum();
        try (Connection c = Database.getConnection()) {
            c.setAutoCommit(false);

            int orderId = orderDAO.createOrder(c, userId, total);
            for (Map.Entry<Product, Integer> e : items.entrySet()) {
                Product p = e.getKey();
                int qty = e.getValue();
                if (!orderDAO.decreaseStock(c, p.getId(), qty)) {
                    c.rollback();
                    return false; // hết hàng
                }
                orderDAO.addItem(c, orderId, p.getId(), qty, p.getPrice());
            }

            c.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
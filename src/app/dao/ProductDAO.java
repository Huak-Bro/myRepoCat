// src/main/java/app/dao/ProductDAO.java
package app.dao;

import app.config.Database;
import app.model.Product;

import java.sql.*;
import java.util.*;

public class ProductDAO {
    public List<Product> findAllActive() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE active = TRUE";
        try (Connection c = Database.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Product> search(String keyword) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE active = TRUE AND title LIKE ?";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean create(Product p) {
        String sql = "INSERT INTO products(title, publisher, issue_date, price, stock, active) VALUES(?,?,?,?,?,?)";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getTitle());
            ps.setString(2, p.getPublisher());
            ps.setString(3, p.getIssueDate());
            ps.setDouble(4, p.getPrice());
            ps.setInt(5, p.getStock());
            ps.setBoolean(6, p.isActive());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean update(Product p) {
        String sql = "UPDATE products SET title=?, publisher=?, issue_date=?, price=?, stock=?, active=? WHERE id=?";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getTitle());
            ps.setString(2, p.getPublisher());
            ps.setString(3, p.getIssueDate());
            ps.setDouble(4, p.getPrice());
            ps.setInt(5, p.getStock());
            ps.setBoolean(6, p.isActive());
            ps.setInt(7, p.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM products WHERE id=?";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private Product map(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setTitle(rs.getString("title"));
        p.setPublisher(rs.getString("publisher"));
        p.setIssueDate(String.valueOf(rs.getDate("issue_date")));
        p.setPrice(rs.getDouble("price"));
        p.setStock(rs.getInt("stock"));
        p.setActive(rs.getBoolean("active"));
        return p;
    }
}
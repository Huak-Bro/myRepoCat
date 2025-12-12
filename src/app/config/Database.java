// src/main/java/app/config/Database.java
package app.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/news_shop?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; // hoặc user bạn tạo
    private static final String PASSWORD = "protein"; // đổi theo máy bạn

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
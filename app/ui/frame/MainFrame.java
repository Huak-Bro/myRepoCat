// src/main/java/app/ui/frame/MainFrame.java
package app.ui.frame;

import app.model.User;
import app.ui.panel.CartPanel;
import app.ui.panel.ProductPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(User u) {
        setTitle("Cửa hàng báo - " + u.getUsername());
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        ProductPanel productPanel = new ProductPanel();
        CartPanel cartPanel = new CartPanel(productPanel.getCart(), u);

        tabs.addTab("Sản phẩm", productPanel);
        tabs.addTab("Giỏ hàng", cartPanel);

        if ("ADMIN".equalsIgnoreCase(u.getRole())) {
            tabs.addTab("Quản trị", new app.ui.panel.AdminPanel());
            tabs.addTab("Báo cáo", new app.ui.panel.ReportPanel());
        }

        add(tabs, BorderLayout.CENTER);
    }
}
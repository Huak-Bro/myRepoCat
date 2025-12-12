// src/main/java/app/ui/panel/AdminPanel.java
package app.ui.panel;

import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JPanel {
    public AdminPanel() {
        setLayout(new BorderLayout());
        add(new JLabel("Khu vực quản trị (CRUD sản phẩm, quản lý đơn) - thêm sau"), BorderLayout.CENTER);
    }
}
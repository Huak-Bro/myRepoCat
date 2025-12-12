// src/main/java/app/ui/panel/CartPanel.java
package app.ui.panel;

import app.model.Product;
import app.model.User;
import app.service.CartService;
import app.service.OrderService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class CartPanel extends JPanel {
    private final CartService cart;
    private final User user;
    private final DefaultTableModel model;
    private final JLabel totalLabel;
    private final OrderService orderService = new OrderService();

    public CartPanel(CartService cart, User user) {
        this.cart = cart;
        this.user = user;

        setLayout(new BorderLayout(8,8));
        model = new DefaultTableModel(new Object[]{"Tiêu đề","Giá","SL"},0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        totalLabel = new JLabel("Tổng: 0 VND");

        JButton refreshBtn = new JButton("Làm mới");
        JButton checkoutBtn = new JButton("Thanh toán");

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(totalLabel);
        bottom.add(refreshBtn);
        bottom.add(checkoutBtn);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> reload());
        checkoutBtn.addActionListener(e -> {
            boolean ok = orderService.checkout(user.getId(), cart.getItems());
            JOptionPane.showMessageDialog(this, ok ? "Đặt hàng thành công" : "Đặt hàng thất bại");
            if (ok) { cart.clear(); reload(); }
        });

        reload();
    }

    private void reload() {
        model.setRowCount(0);
        for (Map.Entry<Product,Integer> e : cart.getItems().entrySet()) {
            Product p = e.getKey();
            int qty = e.getValue();
            model.addRow(new Object[]{p.getTitle(), p.getPrice(), qty});
        }
        totalLabel.setText("Tổng: " + (long) cart.total() + " VND");
    }
}
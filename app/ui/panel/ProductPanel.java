// src/main/java/app/ui/panel/ProductPanel.java
package app.ui.panel;

import app.dao.ProductDAO;
import app.model.Product;
import app.service.CartService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductPanel extends JPanel {
    private final ProductDAO productDAO = new ProductDAO();
    private final CartService cart = new CartService();
    private final DefaultTableModel model;
    private final JTable table;
    private final JTextField searchField;

    public ProductPanel() {
        setLayout(new BorderLayout(8,8));
        searchField = new JTextField();
        JButton searchBtn = new JButton("Tìm");
        JButton addBtn = new JButton("Thêm vào giỏ");

        JPanel top = new JPanel(new BorderLayout(6,6));
        top.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        top.add(searchField, BorderLayout.CENTER);
        top.add(searchBtn, BorderLayout.EAST);

        model = new DefaultTableModel(new Object[]{"ID","Tiêu đề","NXB","Ngày","Giá","Tồn"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(addBtn);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        searchBtn.addActionListener(e -> reload(searchField.getText().trim()));
        addBtn.addActionListener(e -> addSelectedToCart());

        reload(null);
    }

    private void reload(String keyword) {
        List<Product> list = (keyword == null || keyword.isBlank())
                ? productDAO.findAllActive()
                : productDAO.search(keyword);
        model.setRowCount(0);
        for (Product p : list) {
            model.addRow(new Object[]{p.getId(), p.getTitle(), p.getPublisher(), p.getIssueDate(), p.getPrice(), p.getStock()});
        }
    }

    private void addSelectedToCart() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Chọn một sản phẩm trước.");
            return;
        }
        Product p = new Product();
        p.setId((int) model.getValueAt(row, 0));
        p.setTitle((String) model.getValueAt(row, 1));
        p.setPublisher((String) model.getValueAt(row, 2));
        p.setIssueDate((String) model.getValueAt(row, 3));
        p.setPrice(Double.parseDouble(model.getValueAt(row, 4).toString()));
        p.setStock((int) model.getValueAt(row, 5));

        cart.add(p, 1);
        JOptionPane.showMessageDialog(this, "Đã thêm vào giỏ.");
    }

    public CartService getCart() { return cart; }
}
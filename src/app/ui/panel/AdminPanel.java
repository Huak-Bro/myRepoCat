// src/main/java/app/ui/panel/AdminPanel.java
package app.ui.panel;

import app.dao.ProductDAO;
import app.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminPanel extends JPanel {
    private final ProductDAO dao = new ProductDAO();
    private final DefaultTableModel model;

    public AdminPanel() {
        setLayout(new BorderLayout());
        model = new DefaultTableModel(new Object[]{"ID","Tiêu đề","NXB","Ngày","Giá","Tồn","Active"},0);
        JTable table = new JTable(model);
        reload();

        JButton addBtn = new JButton("Thêm");
        JButton editBtn = new JButton("Sửa");
        JButton delBtn = new JButton("Xóa");

        JPanel actions = new JPanel();
        actions.add(addBtn);
        actions.add(editBtn);
        actions.add(delBtn);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            Product p = new Product();
            p.setTitle(JOptionPane.showInputDialog("Tên báo:"));
            p.setPublisher(JOptionPane.showInputDialog("NXB:"));
            p.setIssueDate(JOptionPane.showInputDialog("Ngày phát hành (YYYY-MM-DD):"));
            p.setPrice(Double.parseDouble(JOptionPane.showInputDialog("Giá:")));
            p.setStock(Integer.parseInt(JOptionPane.showInputDialog("Tồn kho:")));
            p.setActive(true);
            dao.create(p);
            reload();
        });

        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row>=0){
                Product p = new Product();
                p.setId((int) model.getValueAt(row,0));
                p.setTitle(JOptionPane.showInputDialog("Tên báo:", model.getValueAt(row,1)));
                p.setPublisher(JOptionPane.showInputDialog("NXB:", model.getValueAt(row,2)));
                p.setIssueDate(JOptionPane.showInputDialog("Ngày:", model.getValueAt(row,3)));
                p.setPrice(Double.parseDouble(JOptionPane.showInputDialog("Giá:", model.getValueAt(row,4))));
                p.setStock(Integer.parseInt(JOptionPane.showInputDialog("Tồn:", model.getValueAt(row,5))));
                p.setActive(true);
                dao.update(p);
                reload();
            }
        });

        delBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row>=0){
                int id = (int) model.getValueAt(row,0);
                dao.delete(id);
                reload();
            }
        });
    }

    private void reload(){
        model.setRowCount(0);
        List<Product> list = dao.findAllActive();
        for(Product p: list){
            model.addRow(new Object[]{p.getId(),p.getTitle(),p.getPublisher(),p.getIssueDate(),p.getPrice(),p.getStock(),p.isActive()});
        }
    }
}
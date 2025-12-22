// src/main/java/app/ui/panel/OrderPanel.java
package app.ui.panel;

import app.config.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class OrderPanel extends JPanel {
    private final DefaultTableModel model;

    public OrderPanel() {
        setLayout(new BorderLayout());
        model = new DefaultTableModel(new Object[]{"ID","UserId","Status","Total","CreatedAt"},0);
        JTable table = new JTable(model);
        reload();

        JButton updateBtn = new JButton("Đánh dấu Completed");
        updateBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row>=0){
                int id = (int) model.getValueAt(row,0);
                try(Connection c = Database.getConnection();
                    PreparedStatement ps = c.prepareStatement("UPDATE orders SET status='COMPLETED' WHERE id=?")){
                    ps.setInt(1,id);
                    ps.executeUpdate();
                    reload();
                }catch(Exception ex){ex.printStackTrace();}
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(updateBtn, BorderLayout.SOUTH);
    }

    private void reload(){
        model.setRowCount(0);
        try(Connection c = Database.getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM orders")){
            while(rs.next()){
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("status"),
                        rs.getDouble("total"),
                        rs.getTimestamp("created_at")
                });
            }
        }catch(Exception e){e.printStackTrace();}
    }
}
// src/main/java/app/ui/panel/ReportPanel.java
package app.ui.panel;

import app.config.Database;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ReportPanel extends JPanel {
    private final JLabel totalLabel = new JLabel("Doanh thu: 0 VND");

    public ReportPanel() {
        setLayout(new BorderLayout());
        JButton refreshBtn = new JButton("Làm mới");
        refreshBtn.addActionListener(e -> reload());
        add(totalLabel, BorderLayout.CENTER);
        add(refreshBtn, BorderLayout.SOUTH);
        reload();
    }

    private void reload(){
        try(Connection c = Database.getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT SUM(total) AS revenue FROM orders WHERE status='COMPLETED'")){
            if(rs.next()){
                totalLabel.setText("Doanh thu: " + rs.getDouble("revenue") + " VND");
            }
        }catch(Exception e){e.printStackTrace();}
    }
}
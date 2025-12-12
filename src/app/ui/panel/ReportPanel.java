// src/main/java/app/ui/panel/ReportPanel.java
package app.ui.panel;

import javax.swing.*;
import java.awt.*;

public class ReportPanel extends JPanel {
    public ReportPanel() {
        setLayout(new BorderLayout());
        add(new JLabel("Báo cáo doanh thu/sản phẩm bán chạy - thêm sau"), BorderLayout.CENTER);
    }
}
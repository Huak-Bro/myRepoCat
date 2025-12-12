// src/main/java/app/ui/frame/LoginFrame.java
package app.ui.frame;

import app.model.User;
import app.service.AuthService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final AuthService authService = new AuthService();

    public LoginFrame() {
        setTitle("Đăng nhập");
        setSize(360, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton loginBtn = new JButton("Đăng nhập");
        JButton registerBtn = new JButton("Đăng ký");

        JPanel form = new JPanel(new GridLayout(0,1,6,6));
        form.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        form.add(new JLabel("Tên đăng nhập"));
        form.add(userField);
        form.add(new JLabel("Mật khẩu"));
        form.add(passField);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.add(registerBtn);
        actions.add(loginBtn);

        add(form, BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);

        loginBtn.addActionListener(e -> {
            String uName = userField.getText().trim();
            String pwd = new String(passField.getPassword());
            User u = authService.login(uName, pwd);
            if (u != null) {
                new MainFrame(u).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Sai thông tin đăng nhập", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerBtn.addActionListener(e -> {
            boolean ok = authService.register(userField.getText().trim(), new String(passField.getPassword()));
            JOptionPane.showMessageDialog(this, ok ? "Đăng ký thành công" : "Đăng ký thất bại");
        });
    }
}
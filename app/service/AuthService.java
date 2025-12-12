// src/main/java/app/service/AuthService.java
package app.service;

import app.dao.UserDAO;
import app.model.User;

public class AuthService {
    private final UserDAO userDAO = new UserDAO();

    public User login(String username, String password) {
        User u = userDAO.findByUsername(username);
        if (u == null) return null;
        return u.getPassword().equals(password) ? u : null;
    }

    public boolean register(String username, String password) {
        return userDAO.create(username, password, "USER");
    }
}
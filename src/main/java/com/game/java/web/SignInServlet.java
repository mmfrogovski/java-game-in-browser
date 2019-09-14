package com.game.java.web;


import com.game.java.model.jdbc.UserDaoImpl;
import com.game.java.model.jdbc.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class SignInServlet extends HttpServlet {
    private UserDaoImpl userDao;

    @Override
    public void init() {
        this.userDao = UserDaoImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/signIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Optional<User> user = userDao.signIn(login);
        String message = isUserValid(user,password);
        if (message.equals("true")) {
            resp.sendRedirect(req.getContextPath() + "/homePage");
        } else {
            req.setAttribute("error", message);
            req.getRequestDispatcher("/WEB-INF/pages/signIn.jsp").forward(req, resp);
        }
    }

    private String isUserValid(Optional<User> user,String password) {
        if (!user.isPresent()) {
            return "User don't exist";
        } else if (!user.get().getPassword().equals(password)) {
            return "Wrong password";
        } else {
            return "true";
        }
    }
}

package com.game.java.web;

import com.game.java.model.jdbc.UserDaoImpl;
import com.game.java.model.jdbc.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogInServlet extends HttpServlet {
    private UserDaoImpl userDao;

    @Override
    public void init() {
        this.userDao = UserDaoImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/logIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password2");
        String message = isUserValid(login,password1,password2);
        if(message.equals("true")){
            userDao.save(new User(login, password1));
            resp.sendRedirect(req.getContextPath() + "/signIn");
        } else {
            req.setAttribute("error", message);
            req.getRequestDispatcher("/WEB-INF/pages/logIn.jsp").forward(req, resp);
        }
    }

    private String isUserValid(String login, String password1, String password2) {
        if (!password1.equals(password2)) {
            return "Passwords don't match";
        } else if (userDao.signIn(login).isPresent()) {
            return "User already exist";
        } else {
            return "true";
        }
    }
}

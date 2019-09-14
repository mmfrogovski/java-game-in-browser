package com.game.java.web;

import com.game.java.model.jdbc.User;
import com.game.java.model.jdbc.UserDao;
import com.game.java.model.jdbc.UserDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomePageServlet extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        this.userDao = UserDaoImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/homePage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        userDao.logOff(user.getLogin());
        req.getSession().removeAttribute("user");
        req.getRequestDispatcher("/WEB-INF/pages/homePage.jsp").forward(req, resp);
    }
}

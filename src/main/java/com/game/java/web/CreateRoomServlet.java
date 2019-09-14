package com.game.java.web;

import com.game.java.model.user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class CreateRoomServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/createRoom.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String login = req.getParameter("name");
//        String password = req.getParameter("password");
//        String message = isUserValid(login, password);
//        if (message.equals("true")) {
//            resp.sendRedirect(req.getContextPath() + "/game");
//        } else {
//            req.setAttribute("error", message);
//            req.getRequestDispatcher("/WEB-INF/pages/signIn.jsp").forward(req, resp);
//        }
    }

//    private String isUserValid(String login, String password) {
//        Optional<User> user = userDao.signIn(login);
//        if (!user.isPresent()) {
//            return "User don't exist";
//        } else if (!user.get().getPassword().equals(password)) {
//            return "Wrong password";
//        } else {
//            return "true";
//        }
//    }
}

package com.game.java.web;

import com.game.java.model.jdbc.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class HomePageServlet extends HttpServlet {
    private UserDao userDao;
    private RoomDao roomDao;

    @Override
    public void init() throws ServletException {
        this.userDao = UserDaoImpl.getInstance();
        this.roomDao = RoomDaoImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/homePage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("user");
        userDao.logOff(user.getLogin());
        Optional<User> finalUser = userDao.getUserById(user.getId());
        if (finalUser.isPresent()) {
            if (finalUser.get().getInRoom() != 0) {
                Optional<Room> room = roomDao.getRoomById(finalUser.get().getInRoom());
                room.ifPresent(room1 -> roomDao.deleteUserFromRoom(finalUser.get(), room1));
            }
        }
        req.getSession().removeAttribute("user");
        resp.sendRedirect(req.getContextPath() + "/homePage");
    }
}

package com.game.java.web;

import com.game.java.model.jdbc.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static java.util.Objects.isNull;

public class CreateRoomServlet extends HttpServlet {
    private RoomDao roomDao;
    private UserDao userDao;

    @Override
    public void init() {
        this.roomDao = RoomDaoImpl.getInstance();
        this.userDao = UserDaoImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (isNull(req.getSession().getAttribute("user"))) {
            resp.sendRedirect(req.getContextPath() + "/homePage");
        } else {
            req.getRequestDispatcher("/WEB-INF/pages/createRoom.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("roomName");
        Optional<Room> room = roomDao.getRoomByName(name);
        if(room.isPresent()) {
            req.setAttribute("error", "Room with the same name already exists");
            req.getRequestDispatcher("/WEB-INF/pages/createRoom.jsp").forward(req, resp);
        } else {
            roomDao.saveRoom(new Room(name));
            room = roomDao.getRoomByName(name);
            if (room.isPresent()) {
                User user = (User) req.getSession().getAttribute("user");
                roomDao.addUserToRoom(user, room.get());
                req.getSession().setAttribute("user", userDao.getUserById(user.getId()).get());
                resp.sendRedirect(req.getContextPath() + "/room/" + room.get().getId());
            } else {
                resp.setStatus(404);
                req.getRequestDispatcher("/WEB-INF/pages/createRoom.jsp").forward(req, resp);
            }
        }
    }
}

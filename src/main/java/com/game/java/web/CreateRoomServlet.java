package com.game.java.web;

import com.game.java.model.jdbc.Room;
import com.game.java.model.jdbc.RoomDao;
import com.game.java.model.jdbc.RoomDaoImpl;
import com.game.java.model.jdbc.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class CreateRoomServlet extends HttpServlet {
    private RoomDao roomDao;

    @Override
    public void init() {
        this.roomDao = RoomDaoImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/createRoom.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("roomName");
        roomDao.saveRoom(new Room(name));
        Optional<Room> room = roomDao.getRoomByName(name);
        if (room.isPresent()) {
            User user =(User) req.getAttribute("user");
            roomDao.addUserToRoom((User) req.getAttribute("user"),room.get().getId());
            resp.sendRedirect(req.getContextPath() + "/room/" + room.get().getId());
        } else {
            resp.setStatus(404);
            req.getRequestDispatcher("/WEB-INF/pages/createRoom.jsp").forward(req, resp);
        }
    }
}

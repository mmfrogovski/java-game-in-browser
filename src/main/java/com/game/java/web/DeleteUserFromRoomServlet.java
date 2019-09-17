package com.game.java.web;

import com.game.java.model.jdbc.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class DeleteUserFromRoomServlet extends HttpServlet {
    private RoomDao roomDao;
    private UserDao userDao;

    @Override
    public void init() {
        this.roomDao = RoomDaoImpl.getInstance();
        this.userDao = UserDaoImpl.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<Room> room = roomDao.getRoomById(getRoomIdFromPath(req));
        if (room.isPresent()) {
            roomDao.deleteUserFromRoom((User) req.getSession().getAttribute("user"), room.get());
            resp.sendRedirect(req.getContextPath() + "/rooms");
            User user = (User) req.getSession().getAttribute("user");
            req.getSession().setAttribute("user", userDao.getUserById(user.getId()).get());
        } else {
            resp.setStatus(404);
            req.getRequestDispatcher("/WEB-INF/pages/rooms.jsp").forward(req, resp);
        }
    }

    protected int getRoomIdFromPath(HttpServletRequest request) {
        return Integer.valueOf(request.getPathInfo().substring(1));
    }
}

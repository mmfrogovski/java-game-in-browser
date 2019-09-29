package com.game.java.web;

import com.game.java.gameWithBotLogic.NumberOfBullsAndCows;
import com.game.java.model.jdbc.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

public class MultiplayerGameServlet extends HttpServlet {
    private User user;

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
            Optional<Room> room = roomDao.getRoomByName(getRoomNameFromPath(req));
            if (room.isPresent()) {
                req.setAttribute("users", roomDao.getUsersFromRoom(room.get()));
                req.getRequestDispatcher("/WEB-INF/pages/multiplayerGame.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String number = req.getParameter("number");
        List<NumberOfBullsAndCows> answersForUser = user.getGameWithBotLogic().gameLogic(number);
        req.setAttribute("answers", answersForUser);
        if (answersForUser.get(answersForUser.size() - 1).getBulls() == 4) {
            req.setAttribute("message", "You win!!!");
            user.setGameWithBotLogic(null);
        }
        req.getRequestDispatcher("/WEB-INF/pages/multiplayerGame.jsp").forward(req, resp);
    }


    protected String getRoomNameFromPath(HttpServletRequest request) {
        return request.getPathInfo().substring(1);
    }
}

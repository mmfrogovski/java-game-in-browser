package com.game.java.web;

import com.game.java.gameWithBotLogic.GameWithBotLogic;
import com.game.java.gameWithBotLogic.NumberOfBullsAndCows;
import com.game.java.model.jdbc.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static java.util.Objects.isNull;

public class GameWithBotServlet extends HttpServlet {
    private User user;

    @Override
    public void init(ServletConfig config) throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (isNull(req.getSession().getAttribute("user"))) {
            resp.sendRedirect(req.getContextPath() + "/homePage");
        } else {
            user = (User) req.getSession().getAttribute("user");
            user.setGameWithBotLogic(new GameWithBotLogic());
            req.getRequestDispatcher("/WEB-INF/pages/gameWithBot.jsp").forward(req, resp);

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
        req.getRequestDispatcher("/WEB-INF/pages/gameWithBot.jsp").forward(req, resp);
    }
}
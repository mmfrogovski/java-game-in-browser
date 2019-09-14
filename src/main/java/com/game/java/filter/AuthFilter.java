package com.game.java.filter;

import com.game.java.model.jdbc.User;
import com.game.java.model.jdbc.UserDao;
import com.game.java.model.jdbc.UserDaoImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static java.util.Objects.nonNull;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain)

            throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        final String login = req.getParameter("login");
        final String password = req.getParameter("password");

        final UserDao userDao = UserDaoImpl.getInstance();

        final HttpSession session = req.getSession();
        final Optional<User> user = userDao.signIn(login);

        //Logged user.
        if (nonNull(session) &&
                nonNull(req.getAttribute("login")) &&
                nonNull(req.getAttribute("password"))){

            filterChain.doFilter(request, response);

        } else if (user.isPresent()) {

            user.get().setAuthoried(true);
            session.setAttribute("user", user.get());

            filterChain.doFilter(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }

}

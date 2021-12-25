package ru.job4j.cars.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.models.User;
import ru.job4j.cars.repositories.HbmUsersRepository;
import ru.job4j.cars.repositories.rules.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        req.getRequestDispatcher("views/user/login-register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        UserStore store = HbmUsersRepository.getInstance();
        String login = req.getParameter("nLogin");
        String pass = req.getParameter("nPassword");
        User user = store.findByLoginAndPassword(login, pass);
        if (user != null) {
            HttpSession sc = req.getSession();
            sc.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/posts.do");
        } else {
            req.setAttribute("error", "Неверный email и/или пароль");
            req.getRequestDispatcher("views/user/login-register.jsp").forward(req, resp);
        }
    }
}

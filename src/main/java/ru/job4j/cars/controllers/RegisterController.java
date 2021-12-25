package ru.job4j.cars.controllers;

import ru.job4j.cars.models.User;
import ru.job4j.cars.repositories.HbmUsersRepository;
import ru.job4j.cars.repositories.rules.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegisterController extends HttpServlet {

    private void errorDispatch(String error, HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        req.setAttribute("error", error);
        req.getRequestDispatcher("views/user/login-register.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        req.getRequestDispatcher("views/user/login-register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        UserStore store = HbmUsersRepository.getInstance();
        String error;
        String pass = req.getParameter("nPassword");
        String check = req.getParameter("nCheckPassword");
        if (pass == null || !pass.equals(check)) {
            error = "Пароль отсутствует или повторён неверно.";
            errorDispatch(error, req, resp);
            return;
        }
        User usr = new User();
        usr.setName(req.getParameter("nName"));
        usr.setLogin(req.getParameter("nLogin"));
        usr.setEmail(req.getParameter("nEmail"));
        usr.setPhone(req.getParameter("nPhone"));
        usr.setPassword(req.getParameter("nPassword"));
        if (!store.save(usr)) {
            error =
                "Ошибка создания пользователя."
                + " Скорее всего пользователь с таким логином"
                + " email`ом или телефоном уже существует.";
            errorDispatch(error, req, resp);
            return;
        }
        usr = store.findByLoginAndPassword(usr.getLogin(), pass);
        if (usr == null) {
            error =
                "Ошибка входа нового пользователя."
                + " Пользователь не найден.";
            errorDispatch(error, req, resp);
            return;
        }
        HttpSession sc = req.getSession();
        sc.setAttribute("user", usr);
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }
}

package ru.job4j.cars.controllers;

import ru.job4j.cars.models.BodyType;
import ru.job4j.cars.repositories.HbmBodyTypesRepository;
import ru.job4j.cars.repositories.rules.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class BodyTypesController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {

		String sid = req.getParameter("id");
		if (sid != null) {
			req.getRequestDispatcher("views/bodyType/edit.jsp").forward(req, resp);
			return;
		}
		req.getRequestDispatcher("views/bodyType/list.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {

		HttpSession s = req.getSession();
		req.setCharacterEncoding("UTF-8");
		if (s.getAttribute("user") == null) {
			s.setAttribute(
				"error",
				"Доступ запрещен! Авторизуйтесь чтобы редактировать данные."
			);
		} else {
			Store<Integer, BodyType> store = HbmBodyTypesRepository.getInstance();
			BodyType bt = BodyType.of(req.getParameter("nName"));
			if (!store.save(bt)) {
				s.setAttribute("error", "Ошибка сохранения типа кузова!");
			}
		}
		resp.sendRedirect(req.getContextPath() + "/body-types.do");
	}
}

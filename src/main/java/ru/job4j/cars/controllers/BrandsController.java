package ru.job4j.cars.controllers;

import ru.job4j.cars.models.Brand;
import ru.job4j.cars.repositories.HbmBrandsRepository;
import ru.job4j.cars.repositories.rules.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class BrandsController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {

		String sid = req.getParameter("id");
		if (sid != null) {
			req.getRequestDispatcher("views/brand/edit.jsp").forward(req, resp);
			return;
		}
		req.getRequestDispatcher("views/brand/list.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {

		HttpSession s = req.getSession();
		req.setCharacterEncoding("UTF-8");
		if (s.getAttribute("user") == null) {
			s.setAttribute(
				"error",
				"Доступ запрещен! Авторизуйтесь, чтобы редактировать данные."
			);
		} else {
			Store<Integer, Brand> store = HbmBrandsRepository.getInstance();
			Brand b = Brand.of(req.getParameter("nName"));
			if (!store.save(b)) {
				s.setAttribute("error", "Ошибка сохранения марки автомобиля!");
			}
		}
		resp.sendRedirect(req.getContextPath() + "/car-brands.do");
	}
}

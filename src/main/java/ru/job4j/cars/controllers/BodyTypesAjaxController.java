package ru.job4j.cars.controllers;

import ru.job4j.cars.models.BodyType;
import ru.job4j.cars.repositories.HbmBodyTypesRepository;
import ru.job4j.cars.repositories.rules.Store;
import ru.job4j.cars.services.JsonService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BodyTypesAjaxController extends HttpServlet {

	private void getBodyTypeById(
		HttpServletRequest req, HttpServletResponse resp, int bodyTypeId
	) throws IOException {

		JsonService js = JsonService.getInstance();
		Store<Integer, BodyType> store = HbmBodyTypesRepository.getInstance();
		resp.setContentType("application/json; charset=utf-8");
		BodyType bt = store.findById(bodyTypeId);
		if (bt != null) {
			js.writeToStream(bt, resp.getOutputStream());
		} else {
			resp.setStatus(204);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {

		String sid = req.getParameter("id");
		if (sid != null) {
			getBodyTypeById(req, resp, Integer.parseInt(sid));
			return;
		}
		JsonService js = JsonService.getInstance();
		Store<Integer, BodyType> store = HbmBodyTypesRepository.getInstance();
		List<BodyType> types = store.findAll();
		if (types != null && !types.isEmpty()) {
			resp.setContentType("application/json; charset=utf-8");
			js.writeToStream(types, resp.getOutputStream());
		} else {
			resp.setStatus(204);
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		JsonService js = JsonService.getInstance();
		Store<Integer, BodyType> store = HbmBodyTypesRepository.getInstance();
		BodyType bt = js.readFromStream(req.getInputStream(), BodyType.class);
		resp.setStatus(store.save(bt) ? 200 : 406);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {

		Store<Integer, BodyType> store = HbmBodyTypesRepository.getInstance();
		String sid = req.getParameter("id");
		int id = sid == null ? 0 : Integer.parseInt(sid);
		resp.setStatus(store.deleteById(id) ? 200 : 406);
	}
}
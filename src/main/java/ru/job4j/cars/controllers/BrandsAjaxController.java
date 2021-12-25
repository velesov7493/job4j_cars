package ru.job4j.cars.controllers;

import ru.job4j.cars.models.Brand;
import ru.job4j.cars.repositories.HbmBrandsRepository;
import ru.job4j.cars.repositories.rules.Store;
import ru.job4j.cars.services.JsonService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BrandsAjaxController extends HttpServlet {

	private void getCarBrandById(HttpServletRequest req, HttpServletResponse resp, int brandId)
		throws IOException {

		JsonService js = JsonService.getInstance();
		Store<Integer, Brand> store = HbmBrandsRepository.getInstance();
		resp.setContentType("application/json; charset=utf-8");
		Brand b = store.findById(brandId);
		if (b != null) {
			js.writeToStream(b, resp.getOutputStream());
		} else {
			resp.setStatus(204);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {

		String sid = req.getParameter("id");
		if (sid != null) {
			getCarBrandById(req, resp, Integer.parseInt(sid));
			return;
		}
		JsonService js = JsonService.getInstance();
		Store<Integer, Brand> store = HbmBrandsRepository.getInstance();
		List<Brand> brands = store.findAll();
		if (brands != null && !brands.isEmpty()) {
			resp.setContentType("application/json; charset=utf-8");
			js.writeToStream(brands, resp.getOutputStream());
		} else {
			resp.setStatus(204);
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		JsonService js = JsonService.getInstance();
		Store<Integer, Brand> store = HbmBrandsRepository.getInstance();
		Brand b = js.readFromStream(req.getInputStream(), Brand.class);
		resp.setStatus(store.save(b) ? 200 : 406);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {

		Store<Integer, Brand> store = HbmBrandsRepository.getInstance();
		String sid = req.getParameter("id");
		int id = sid == null ? 0 : Integer.parseInt(sid);
		resp.setStatus(store.deleteById(id) ? 200 : 406);
	}
}

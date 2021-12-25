package ru.job4j.cars.controllers;

import ru.job4j.cars.models.Post;
import ru.job4j.cars.services.JsonService;
import ru.job4j.cars.services.PostsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PostsAjaxController extends HttpServlet {

	private void getPostById(HttpServletRequest req, HttpServletResponse resp, int postId)
		throws IOException {

		PostsService service = PostsService.getInstance();
		JsonService jsonService = JsonService.getInstance();
		resp.setContentType("application/json; charset=utf-8");
		Post p = service.findById(postId);
		if (p != null) {
			jsonService.writeToStream(p, resp.getOutputStream());
		} else {
			resp.setStatus(204);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {

		String sid = req.getParameter("id");
		if (sid != null) {
			getPostById(req, resp, Integer.parseInt(sid));
			return;
		}
		PostsService service = PostsService.getInstance();
		JsonService jsonService = JsonService.getInstance();
		resp.setContentType("application/json; charset=utf-8");
		Integer brandId =
			req.getParameter("brandId") == null
			? null : Integer.parseInt(req.getParameter("brandId"));
		Integer bodyTypeId =
			req.getParameter("bodyTypeId") == null
			? null : Integer.parseInt(req.getParameter("bodyTypeId"));
		Integer authorId =
			req.getParameter("authorId") == null
				? null : Integer.parseInt(req.getParameter("authorId"));
		List<Post> posts = service.findAllByDimensions(
			false, brandId, bodyTypeId, authorId
		);
		if (posts != null && !posts.isEmpty()) {
			jsonService.writeToStream(posts, resp.getOutputStream());
		} else {
			resp.setStatus(204);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {

		PostsService service = PostsService.getInstance();
		String sid = req.getParameter("id");
		int id = sid == null ? 0 : Integer.parseInt(sid);
		resp.setStatus(service.deleteById(id) ? 200 : 406);
	}
}

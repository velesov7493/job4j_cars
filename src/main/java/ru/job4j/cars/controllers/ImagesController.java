package ru.job4j.cars.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.models.Photo;
import ru.job4j.cars.repositories.CmbPhotosRepository;
import ru.job4j.cars.repositories.rules.PhotoStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ImagesController extends HttpServlet {

	private static final Logger LOG = LoggerFactory.getLogger(ImagesController.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {

		String sid = req.getParameter("id");
		int imageId;
		try {
			imageId = sid != null ? Integer.parseInt(sid) : 0;
		} catch (NumberFormatException nfe) {
			imageId = 0;
		}
		PhotoStore store = CmbPhotosRepository.getInstance();
		Photo photo = imageId == 0 ? null : store.findById(imageId);
		if (photo != null) {
			resp.setContentType(photo.getMimeType());
			if (!store.writeImageToStream(photo, resp.getOutputStream())) {
				LOG.warn("Изображение с id=" + photo.getId() + " не загружено!");
				resp.setStatus(404);
			}
		}
	}
}

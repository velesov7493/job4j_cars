package ru.job4j.cars.controllers;

import ru.job4j.cars.models.Post;
import ru.job4j.cars.models.User;
import ru.job4j.cars.services.PostsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@MultipartConfig(maxFileSize = 16777216L, maxRequestSize = 33554432L)
public class PostsController extends HttpServlet {

    private String partAsString(HttpServletRequest req, String partName)
        throws ServletException, IOException {

        Part p = req.getPart(partName);
        return
                p != null
                ? new String(
                    req.getPart(partName).getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8
                ) : "";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        String sid = req.getParameter("id");

        if (sid != null) {
            req.getRequestDispatcher("views/post/edit.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("views/post/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        HttpSession s = req.getSession();
        User author = (User) s.getAttribute("user");
        PostsService service = PostsService.getInstance();
        int postId = Integer.parseInt(partAsString(req, "nId"));
        String model = partAsString(req, "nModel");
        float price = Float.parseFloat(partAsString(req, "nPrice"));
        int bodyTypeId = Integer.parseInt(partAsString(req, "nBodyType"));
        int brandId = Integer.parseInt(partAsString(req, "nBrand"));
        String sDescription = partAsString(req, "nDescription");
        String sSold = partAsString(req, "nSold");
        Post p;
        if (postId == 0) {
            p = new Post();
            p.setAuthor(author);
        } else {
            p = service.findById(postId);
            if (!p.getAuthor().equals(author)) {
                s.setAttribute("error", "Это не Ваше объявление!");
                resp.sendRedirect(req.getContextPath() + "/posts.do?id=" + postId);
                return;
            }
        }
        p.setModel(model);
        p.setDescription(sDescription);
        p.setSold("on".equals(sSold));
        p.setPrice(price);
        Part photo = req.getPart("nPhoto");
        String error;
        if (photo != null && photo.getSize() > 0) {
            error =
                service.saveWithPhoto(p, photo.getInputStream(), bodyTypeId, brandId)
                ? null : "Провал сохранения объявления с фотографией!";
        } else {
            error =
                service.save(p, bodyTypeId, brandId)
                ? null : "Провал сохранения объявления без фотографии!";
        }
        if (error != null) {
            s.setAttribute("error", error);
            resp.sendRedirect(req.getContextPath() + "/posts.do?id=" + postId);
        } else {
            resp.sendRedirect(req.getContextPath() + "/posts.do");
        }
    }
}
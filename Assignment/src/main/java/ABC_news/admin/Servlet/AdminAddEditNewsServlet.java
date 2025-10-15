package ABC_news.admin.Servlet;

import ABC_news.DAO.CategoryDAO;
import ABC_news.DAO.CategoryDAOImpl;
import ABC_news.DAO.NewsDAO;
import ABC_news.DAO.NewsDAOImpl;
import ABC_news.Entity.Category;
import ABC_news.Entity.News;
import ABC_news.Entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@WebServlet("/admin/add_edit_news")
@MultipartConfig
public class AdminAddEditNewsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        CategoryDAO categoryDAO = new CategoryDAOImpl();
        List<Category> categories = categoryDAO.findAll();
        req.setAttribute("categories", categories);

        String id = req.getParameter("id");
        if (id != null && !id.isEmpty()) {
            NewsDAO newsDAO = new NewsDAOImpl();
            News news = newsDAO.findById(id);
            req.setAttribute("news", news);
        }

        req.getRequestDispatcher("/admin/edit_news.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String id = req.getParameter("id");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String categoryId = req.getParameter("categoryId");
        String positionStr = req.getParameter("position");
        String homeParam = req.getParameter("home");
        String status = req.getParameter("status");
        String oldImage = req.getParameter("oldImage");

        boolean home = "1".equals(homeParam) || "true".equalsIgnoreCase(homeParam);
        Integer position = (positionStr != null && !positionStr.isEmpty()) ? Integer.valueOf(positionStr) : null;

        // Upload ảnh
        Part imagePart = req.getPart("image");
        String fileName = null;
        String uploadPath = getServletContext().getRealPath("/uploads");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        if (imagePart != null && imagePart.getSize() > 0) {
            fileName = UUID.randomUUID() + "_" + imagePart.getSubmittedFileName();
            imagePart.write(uploadPath + File.separator + fileName);
        } else {
            fileName = oldImage;
        }

        News news = new News();
        news.setId((id == null || id.isEmpty()) ? UUID.randomUUID().toString() : id);
        news.setTitle(title);
        news.setContent(content);
        news.setImage(fileName != null ? fileName : "default.jpg");
        news.setPostedDate(new Date(System.currentTimeMillis()));
        news.setAuthor(user.getId());
        news.setViewCount(0);
        news.setCategoryId(categoryId);
        news.setHome(home);
        news.setPosition(position);
        news.setStatus(status != null ? status : "Chưa duyệt");

        NewsDAOImpl newsDAO = new NewsDAOImpl();
        boolean result = (id == null || id.isEmpty()) ? newsDAO.insert(news) : newsDAO.update(news);

        if (result) {
            if (user.isRole()) {
                resp.sendRedirect(req.getContextPath() + "/admin/manage_all_news?success=true");
            } else {
            	resp.sendRedirect(req.getContextPath() + "/reporter?success=true");

            }
        } else {
            if (user.isRole()) {
                resp.sendRedirect(req.getContextPath() + "/admin/add_edit_news?error=true");
            } else {
                resp.sendRedirect(req.getContextPath() + "/reporter/edit_news.jsp?error=true");
            }
        }
    }
}

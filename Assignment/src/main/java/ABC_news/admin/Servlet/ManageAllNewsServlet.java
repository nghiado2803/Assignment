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

@WebServlet({
    "/admin/manage_all_news",
    "/admin/add_edit_news",
    "/admin/delete_news"
})
@MultipartConfig
public class ManageAllNewsServlet extends HttpServlet {

    private final NewsDAO newsDAO = new NewsDAOImpl();
    private final CategoryDAO categoryDAO = new CategoryDAOImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String path = req.getServletPath();

        switch (path) {
            case "/admin/manage_all_news":
                showAllNews(req, resp);
                break;
            case "/admin/add_edit_news":
                if ("GET".equalsIgnoreCase(req.getMethod())) {
                    showAddEditPage(req, resp);
                } else {
                    handleAddEdit(req, resp);
                }
                break;
            case "/admin/delete_news":
                handleDelete(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showAllNews(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<News> newsList = newsDAO.findAll();
        req.setAttribute("newsList", newsList);

        HttpSession session = req.getSession();
        String message = (String) session.getAttribute("message");
        if (message != null) {
            req.setAttribute("message", message);
            session.removeAttribute("message");
        }

        req.getRequestDispatcher("/admin/manage_all_news.jsp").forward(req, resp);
    }

    private void showAddEditPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Category> categories = categoryDAO.findAll();
        req.setAttribute("categories", categories);

        String id = req.getParameter("id");
        if (id != null && !id.isEmpty()) {
            News news = newsDAO.findById(id);
            req.setAttribute("news", news);
        }

        req.getRequestDispatcher("/admin/edit_news.jsp").forward(req, resp);
    }

    private void handleAddEdit(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

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

        boolean result = (id == null || id.isEmpty()) ? newsDAO.insert(news) : newsDAO.update(news);

        if (result) {
            session.setAttribute("message", (id == null || id.isEmpty())
                    ? "Thêm tin thành công!"
                    : "Cập nhật tin thành công!");
        } else {
            session.setAttribute("message", "Lưu tin thất bại!");
        }

        resp.sendRedirect(req.getContextPath() + "/admin/manage_all_news");
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String id = req.getParameter("id");

        if (id != null && !id.isEmpty()) {
            boolean success = newsDAO.delete(id);
            req.getSession().setAttribute("message",
                    success ? "Xóa tin thành công!"
                            : "Không thể xóa tin!");
        } else {
            req.getSession().setAttribute("message", "ID tin không hợp lệ!");
        }

        resp.sendRedirect(req.getContextPath() + "/admin/manage_all_news");
    }
}

package ABC_news.admin.Servlet;

import ABC_news.DAO.NewsDAO;
import ABC_news.DAO.NewsDAOImpl;
import ABC_news.Entity.News;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/feature_news")
public class FeatureNewsServlet extends HttpServlet {

    private NewsDAO newsDAO;

    @Override
    public void init() throws ServletException {
        newsDAO = new NewsDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");

        if (id == null || id.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?error=missing_id");
            return;
        }

        News news = newsDAO.getNewsById(id);
        if (news == null) {
            response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?error=not_found");
            return;
        }

        if (!"Đã duyệt".equalsIgnoreCase(news.getStatus())) {
            response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?error=not_approved");
            return;
        }

        try {
            news.setHome(true);
            newsDAO.update(news);

            newsDAO.updateFeature(id, true);

            response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?success=featured");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?error=exception");
        }
    }
}

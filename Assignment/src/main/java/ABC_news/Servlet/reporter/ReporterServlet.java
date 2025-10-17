package ABC_news.Servlet.reporter;

import ABC_news.DAO.NewsDAO;
import ABC_news.DAO.NewsDAOImpl;
import ABC_news.Entity.News;
import ABC_news.Entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet({
    "/reporter",
    "/edit_news",
    "/delete_news"
})
public class ReporterServlet extends HttpServlet {
    private final NewsDAO newsDAO = new NewsDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/index/index.jsp");
            return;
        }

        String path = req.getServletPath();

        switch (path) {
            case "/edit_news" -> handleEditNews(req, resp, user);
            case "/delete_news" -> handleDeleteNews(req, resp, user);
            default -> handleListNews(req, resp, user);
        }
    }

    private void handleListNews(HttpServletRequest req, HttpServletResponse resp, User user)
            throws ServletException, IOException {

        List<News> list = newsDAO.selectByAuthor(user.getId());

        List<News> topViewed = newsDAO.findTopViewedApproved(5);
        List<News> latestNews = newsDAO.findLatestApproved(5);
        List<News> homeLeft = newsDAO.findHomeByPosition(1);

        req.setAttribute("newsList", list);
        req.setAttribute("topViewed", topViewed);
        req.setAttribute("latestNews", latestNews);
        req.setAttribute("homeLeft", homeLeft);
        req.setAttribute("fullname", user.getFullname());

        req.getRequestDispatcher("/reporter/reporter.jsp").forward(req, resp);
    }

    private void handleEditNews(HttpServletRequest req, HttpServletResponse resp, User user)
            throws ServletException, IOException {

        String id = req.getParameter("id");

        if ("GET".equalsIgnoreCase(req.getMethod())) {
            if (id == null || id.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/reporter");
                return;
            }

            News news = newsDAO.findById(id);
            if (news == null) {
                req.getSession().setAttribute("message", "Không tìm thấy tin tức!");
                resp.sendRedirect(req.getContextPath() + "/reporter");
                return;
            }

            req.setAttribute("news", news);
            req.getRequestDispatcher("/reporter/edit_news.jsp").forward(req, resp);
        } else {
            req.setCharacterEncoding("UTF-8");
            String title = req.getParameter("title");
            String content = req.getParameter("content");
            String image = req.getParameter("image");
            String categoryId = req.getParameter("categoryId");
            String postedDateStr = req.getParameter("postedDate");

            try {
                Date postedDate = Date.valueOf(postedDateStr);
                News news = new News();
                news.setId(id);
                news.setTitle(title);
                news.setContent(content);
                news.setImage(image);
                news.setCategoryId(categoryId);
                news.setPostedDate(postedDate);
                news.setAuthor(user.getId());

                boolean updated = newsDAO.update(news);

                req.getSession().setAttribute("message",
                        updated ? "Cập nhật tin thành công!" : "Cập nhật thất bại!");

            } catch (Exception e) {
                e.printStackTrace();
                req.getSession().setAttribute("message", "Lỗi dữ liệu nhập!");
            }

            resp.sendRedirect(req.getContextPath() + "/reporter");
        }
    }

    private void handleDeleteNews(HttpServletRequest req, HttpServletResponse resp, User user)
            throws IOException {

        String id = req.getParameter("id");

        if (id == null || id.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/reporter");
            return;
        }

        boolean deleted = newsDAO.delete(id);
        req.getSession().setAttribute("message",
                deleted ? "Xóa tin thành công!" : "Không thể xóa tin này!");

        resp.sendRedirect(req.getContextPath() + "/reporter");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getServletPath();
        User user = (User) req.getSession().getAttribute("user");

        if ("/edit_news".equals(path)) {
            handleEditNews(req, resp, user);
        } else {
            doGet(req, resp);
        }
    }
}

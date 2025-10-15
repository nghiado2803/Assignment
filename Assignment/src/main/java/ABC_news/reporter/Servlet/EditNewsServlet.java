package ABC_news.reporter.Servlet;

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

@WebServlet("/edit_news")
public class EditNewsServlet extends HttpServlet {
    private final NewsDAO newsDAO = new NewsDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String id = req.getParameter("id");
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
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String image = req.getParameter("image");
        String categoryId = req.getParameter("categoryId");
        String postedDateStr = req.getParameter("postedDate");

        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/index/index.jsp");
            return;
        }

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

            if (updated) {
                req.getSession().setAttribute("message", "Cập nhật tin thành công!");
            } else {
                req.getSession().setAttribute("message", "Cập nhật thất bại!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.getSession().setAttribute("message", "Lỗi dữ liệu nhập!");
        }

        resp.sendRedirect(req.getContextPath() + "/reporter");
    }
}

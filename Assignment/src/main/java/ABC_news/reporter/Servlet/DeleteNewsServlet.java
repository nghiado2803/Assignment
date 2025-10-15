package ABC_news.reporter.Servlet;

import ABC_news.DAO.NewsDAO;
import ABC_news.DAO.NewsDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete_news")
public class DeleteNewsServlet extends HttpServlet {
    private final NewsDAO newsDAO = new NewsDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String id = req.getParameter("id");

        if (id == null || id.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/reporter");
            return;
        }

        boolean deleted = newsDAO.delete(id);
        if (deleted) {
            System.out.println("[LOG] Đã xóa tin: " + id);
            req.getSession().setAttribute("message", "Xóa tin thành công!");
        } else {
            System.out.println("[LOG] Xóa tin thất bại: " + id);
            req.getSession().setAttribute("message", "Không thể xóa tin này!");
        }

        resp.sendRedirect(req.getContextPath() + "/reporter");
    }
}

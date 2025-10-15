package ABC_news.admin.Servlet;

import java.io.IOException;

import ABC_news.DAO.NewsDAO;
import ABC_news.DAO.NewsDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/delete_news")
public class DeleteNewsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");

        if (id != null && !id.isEmpty()) {
            NewsDAO dao = new NewsDAOImpl();
            boolean success = dao.delete(id);

            if (success) {
                System.out.println(">>> Xóa tin thành công: " + id);
                request.getSession().setAttribute("message", "Xóa tin thành công!");
            } else {
                System.out.println(">>> Xóa tin thất bại: " + id);
                request.getSession().setAttribute("message", "Không thể xóa tin!");
            }
        } else {
            request.getSession().setAttribute("message", "ID tin không hợp lệ!");
        }

        response.sendRedirect(request.getContextPath() + "/admin/manage_all_news");    }
}

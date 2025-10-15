package ABC_news.admin.Servlet;

import java.io.IOException;

import ABC_news.DAO.NewsDAO;
import ABC_news.DAO.NewsDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/approve_news")
public class ApproveNewsServlet extends HttpServlet {

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

        try {
            boolean statusUpdated = newsDAO.updateStatus(id, "Đã duyệt");

            newsDAO.updateApproved(id, true);

            if (statusUpdated) {
                System.out.println("Tin ID " + id + " đã được duyệt!");
                response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?success=approved");
            } else {
                System.out.println("Không thể duyệt tin ID " + id);
                response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?error=update_failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?error=exception");
        }
    }
}

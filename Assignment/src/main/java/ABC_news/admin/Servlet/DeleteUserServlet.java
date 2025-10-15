package ABC_news.admin.Servlet;

import java.io.IOException;

import ABC_news.DAO.UserDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/delete_user")
public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");

        UserDAOImpl dao = new UserDAOImpl();
        dao.delete(id);

        response.sendRedirect(request.getContextPath() + "/admin/manage_users");
    }
}

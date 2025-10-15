package ABC_news.admin.Servlet;

import ABC_news.DAO.CategoryDAOImpl;
import ABC_news.Entity.Category;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/manage_categories")
public class ManageCategoriesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Category> list = new CategoryDAOImpl().findAll();
        req.setAttribute("categories", list);

        req.getRequestDispatcher("/admin/manage_categories.jsp").forward(req, resp);
    }
}

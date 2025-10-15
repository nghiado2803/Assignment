package ABC_news.admin.Servlet;

import ABC_news.DAO.CategoryDAOImpl;
import ABC_news.Entity.Category;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/edit_category")
public class EditCategoryServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String id = req.getParameter("categoryId");
        String name = req.getParameter("categoryName");

        Category c = new Category(id, name);

        boolean success = new CategoryDAOImpl().update(c);
        if (success) {
            resp.sendRedirect(req.getContextPath() + "/admin/manage_categories");
        } else {
            resp.getWriter().println("Cập nhật loại tin thất bại");
        }
    }
}

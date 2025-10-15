package ABC_news.admin.Servlet;

import ABC_news.DAO.CategoryDAOImpl;
import ABC_news.Entity.Category;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/add_category")
public class AddCategoryServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String name = req.getParameter("categoryName");
        String id = "C" + String.format("%02d", (int)(Math.random() * 90 + 10));

        Category c = new Category(id, name);

        boolean success = new CategoryDAOImpl().insert(c);
        if (success) {
            resp.sendRedirect(req.getContextPath() + "/admin/manage_categories");
        } else {
            resp.getWriter().println("❌ Thêm loại tin thất bại");
        }
    }
}

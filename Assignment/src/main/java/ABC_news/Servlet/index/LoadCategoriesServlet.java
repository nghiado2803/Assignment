package ABC_news.Servlet.index;

import ABC_news.DAO.CategoryDAOImpl;
import ABC_news.Entity.Category;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;


@WebServlet("/load_categories")
public class LoadCategoriesServlet extends HttpServlet {

    private CategoryDAOImpl categoryDAO;

    @Override
    public void init() throws ServletException {
        categoryDAO = new CategoryDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Category> categories = categoryDAO.findAll();

        HttpSession session = req.getSession();
        session.setAttribute("navCategories", categories);

        resp.sendRedirect(req.getContextPath() + "/index");
    }
}
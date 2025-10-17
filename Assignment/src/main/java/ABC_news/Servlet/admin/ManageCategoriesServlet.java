package ABC_news.Servlet.admin;

import ABC_news.DAO.CategoryDAOImpl;
import ABC_news.Entity.Category;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet({
    "/admin/manage_categories",
    "/admin/add_category",
    "/admin/edit_category",
    "/admin/delete_category"
})
public class ManageCategoriesServlet extends HttpServlet {

    private CategoryDAOImpl categoryDAO;

    @Override
    public void init() throws ServletException {
        categoryDAO = new CategoryDAOImpl();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();

        switch (path) {
            case "/admin/manage_categories":
                showCategoryList(req, resp);
                break;

            case "/admin/add_category":
                addCategory(req, resp);
                break;

            case "/admin/edit_category":
                editCategory(req, resp);
                break;

            case "/admin/delete_category":
                deleteCategory(req, resp);
                break;

            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void showCategoryList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Category> list = categoryDAO.findAll();
        req.setAttribute("categories", list);

        HttpSession session = req.getSession();
        String message = (String) session.getAttribute("message");
        if (message != null) {
            req.setAttribute("message", message);
            session.removeAttribute("message");
        }

        req.getRequestDispatcher("/admin/manage_categories.jsp").forward(req, resp);
    }

    private void addCategory(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String name = req.getParameter("categoryName");
        String id = "C" + String.format("%02d", (int) (Math.random() * 90 + 10));

        Category c = new Category(id, name);
        boolean success = categoryDAO.insert(c);

        if (success) {
            req.getSession().setAttribute("message", "Thêm loại tin thành công!");
        } else {
            req.getSession().setAttribute("message", "Thêm loại tin thất bại!");
        }

        resp.sendRedirect(req.getContextPath() + "/admin/manage_categories");
    }

    private void editCategory(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String id = req.getParameter("categoryId");
        String name = req.getParameter("categoryName");

        Category c = new Category(id, name);
        boolean success = categoryDAO.update(c);

        if (success) {
            req.getSession().setAttribute("message", "Cập nhật loại tin thành công!");
        } else {
            req.getSession().setAttribute("message", "Cập nhật loại tin thất bại!");
        }

        resp.sendRedirect(req.getContextPath() + "/admin/manage_categories");
    }

    private void deleteCategory(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String id = req.getParameter("id");

        if (id != null && !id.trim().isEmpty()) {
            boolean deleted = categoryDAO.delete(id);

            if (deleted) {
                req.getSession().setAttribute("message", "Xóa loại tin thành công!");
            } else {
                req.getSession().setAttribute("message", "Không thể xóa loại tin (đang được sử dụng)!");
            }
        } else {
            req.getSession().setAttribute("message", "Mã loại tin không hợp lệ!");
        }

        resp.sendRedirect(req.getContextPath() + "/admin/manage_categories");
    }
}

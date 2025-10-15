package ABC_news.admin.Servlet;

import ABC_news.DAO.CategoryDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/delete_category")
public class DeleteCategoryServlet extends HttpServlet {

    private CategoryDAOImpl categoryDAO;

    @Override
    public void init() throws ServletException {
        categoryDAO = new CategoryDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");

        if (id != null && !id.trim().isEmpty()) {
            boolean deleted = categoryDAO.delete(id);

            if (deleted) {
                request.getSession().setAttribute("message", " Xóa loại tin thành công!");
            } else {
                request.getSession().setAttribute("message", " Không thể xóa loại tin (có thể đang được sử dụng)!");
            }
        } else {
            request.getSession().setAttribute("message", " Mã loại tin không hợp lệ!");
        }

        response.sendRedirect(request.getContextPath() + "/admin/manage_categories");
    }
}

package ABC_news.index.Servlet;

import ABC_news.DAO.UserDAO;
import ABC_news.DAO.UserDAOImpl;
import ABC_news.Entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/changePassword")
public class ChangePasswordServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.getWriter().write("<script>alert('Phiên đăng nhập đã hết hạn!'); window.location='/index';</script>");
            return;
        }

        String oldPass = req.getParameter("oldPassword");
        String newPass = req.getParameter("newPassword");
        String confirm = req.getParameter("confirmPassword");

        if (!user.getPassword().equals(oldPass)) {
            resp.getWriter().write("<script>alert('Mật khẩu cũ không đúng!'); history.back();</script>");
            return;
        }

        if (!newPass.equals(confirm)) {
            resp.getWriter().write("<script>alert('Xác nhận mật khẩu không khớp!'); history.back();</script>");
            return;
        }

        user.setPassword(newPass);
        boolean success = userDAO.updatePassword(user.getId(), newPass);

        if (success) {
            session.setAttribute("user", user);

            String redirectUrl;
            if (user.isRole()) { 
                redirectUrl = "admin/admin.jsp";
            } else { 
                redirectUrl = "reporter/reporter.jsp";
            }

            resp.getWriter().write("<script>alert('Đổi mật khẩu thành công!'); window.location='" + redirectUrl + "';</script>");
        } else {
            resp.getWriter().write("<script>alert('Lỗi khi đổi mật khẩu! Vui lòng thử lại.'); history.back();</script>");
        }
    }
}

package ABC_news.Servlet.admin;

import ABC_news.DAO.UserDAO;
import ABC_news.DAO.UserDAOImpl;
import ABC_news.Entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet({
    "/admin/manage_users",
    "/admin/add_user",
    "/admin/edit_user",
    "/admin/delete_user"
})
public class ManageUsersServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAOImpl();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();

        switch (path) {
            case "/admin/manage_users":
                showUserList(req, resp);
                break;
            case "/admin/add_user":
                addUser(req, resp);
                break;
            case "/admin/edit_user":
                editUser(req, resp);
                break;
            case "/admin/delete_user":
                deleteUser(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void showUserList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<User> users = userDAO.getAllUsers();
        req.setAttribute("users", users);

        HttpSession session = req.getSession();
        String message = (String) session.getAttribute("message");
        if (message != null) {
            req.setAttribute("message", message);
            session.removeAttribute("message");
        }

        req.getRequestDispatcher("/admin/manage_users.jsp").forward(req, resp);
    }

    private void addUser(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String id = req.getParameter("id");
        String password = req.getParameter("password");
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String mobile = req.getParameter("mobile");
        String birthday = req.getParameter("birthday");
        boolean gender = Boolean.parseBoolean(req.getParameter("gender"));
        boolean role = Boolean.parseBoolean(req.getParameter("role"));

        User user = new User();
        user.setId(id);
        user.setPassword(password);
        user.setFullname(fullname);
        user.setEmail(email);
        user.setMobile(mobile);
        if (birthday != null && !birthday.isEmpty()) {
            user.setBirthday(LocalDate.parse(birthday));
        }
        user.setGender(gender);
        user.setRole(role);

        boolean success = userDAO.insert(user);
        req.getSession().setAttribute("message",
                success ? "Thêm người dùng thành công!"
                        : "Thêm người dùng thất bại!");

        resp.sendRedirect(req.getContextPath() + "/admin/manage_users");
    }

    private void editUser(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");

        String id = req.getParameter("id");
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String mobile = req.getParameter("mobile");
        String birthday = req.getParameter("birthday");
        String genderStr = req.getParameter("gender");
        String roleStr = req.getParameter("role");

        User existingUser = userDAO.findById(id);
        if (existingUser == null) {
            session.setAttribute("message", "Không tìm thấy người dùng cần cập nhật!");
            resp.sendRedirect(req.getContextPath() + "/admin/manage_users");
            return;
        }

        existingUser.setFullname(fullname);
        existingUser.setEmail(email);
        existingUser.setMobile(mobile);
        existingUser.setGender("1".equals(genderStr));

        if (currentUser != null && currentUser.isRole()) {
            existingUser.setRole("1".equals(roleStr));
        }

        if (birthday != null && !birthday.isEmpty()) {
            try {
                existingUser.setBirthday(LocalDate.parse(birthday));
            } catch (Exception e) {
                session.setAttribute("message", "Ngày sinh không hợp lệ!");
                resp.sendRedirect(req.getContextPath() + "/admin/manage_users");
                return;
            }
        }

        if (password != null && !password.trim().isEmpty()) {
            existingUser.setPassword(password);
        }

        boolean success = userDAO.update(existingUser);
        session.setAttribute("message",
                success ? "Cập nhật người dùng thành công!"
                        : "Cập nhật người dùng thất bại!");

        resp.sendRedirect(req.getContextPath() + "/admin/manage_users");
    }



    private void deleteUser(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String id = req.getParameter("id");

        User targetUser = userDAO.findById(id);
        if (targetUser != null && targetUser.isRole()) { 
            req.getSession().setAttribute("message", "Không thể xóa tài khoản quản trị!");
            resp.sendRedirect(req.getContextPath() + "/admin/manage_users");
            return;
        }

        boolean deleted = userDAO.delete(id);
        req.getSession().setAttribute("message",
                deleted ? "Xóa người dùng thành công!"
                        : "Không thể xóa người dùng!");

        resp.sendRedirect(req.getContextPath() + "/admin/manage_users");
    }

}

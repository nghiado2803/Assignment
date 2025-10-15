package ABC_news.admin.Servlet;

import ABC_news.DAO.UserDAO;
import ABC_news.DAO.UserDAOImpl;
import ABC_news.Entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/admin/add_user")
public class AddUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String password = request.getParameter("password");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String birthday = request.getParameter("birthday");
        boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
        boolean role = Boolean.parseBoolean(request.getParameter("role"));

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

        UserDAOImpl dao = new UserDAOImpl();
        dao.insert(user);

        response.sendRedirect(request.getContextPath() + "/admin/manage_users");
    }
}
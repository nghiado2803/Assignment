package ABC_news.admin.Servlet;

import java.io.IOException;
import java.time.LocalDate;

import ABC_news.DAO.UserDAOImpl;
import ABC_news.Entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/edit_user")
public class EditUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String mobile = request.getParameter("mobile");
        String birthday = request.getParameter("birthday");
        boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
        boolean role = Boolean.parseBoolean(request.getParameter("role"));

        User user = new User();
        user.setId(id);
        user.setFullname(fullname);
        user.setEmail(email);
        user.setPassword(password);
        user.setMobile(mobile);
        if (birthday != null && !birthday.isEmpty()) {
            user.setBirthday(LocalDate.parse(birthday));
        }
        user.setGender(gender);
        user.setRole(role);

        UserDAOImpl dao = new UserDAOImpl();
        dao.update(user);

        response.sendRedirect(request.getContextPath() + "/admin/manage_users");
    }
}

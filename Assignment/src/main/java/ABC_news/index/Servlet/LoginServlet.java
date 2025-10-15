package ABC_news.index.Servlet;

import ABC_news.DAO.UserDAOImpl;
import ABC_news.Entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");

            if (user.isRole()) {
                response.sendRedirect(request.getContextPath() + "/admin");
            } else {
                response.sendRedirect(request.getContextPath() + "/reporter");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/index/index.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String redirectURL = request.getParameter("redirectURL");

        UserDAOImpl dao = new UserDAOImpl();
        User user = dao.login(email, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            if (user.isRole()) {
                response.sendRedirect(request.getContextPath() + "/admin");
            } else {
                if (redirectURL != null && !redirectURL.isEmpty()) {
                    response.sendRedirect(request.getContextPath() + redirectURL.replace(request.getContextPath(), ""));
                } else {
                    response.sendRedirect(request.getContextPath() + "/reporter"); 
                }
            }

        } else {
            
            if (redirectURL == null || redirectURL.isEmpty()) {
                redirectURL = "/index";
            }
            response.sendRedirect(request.getContextPath() + redirectURL + "?error=1");
        }
    }

}

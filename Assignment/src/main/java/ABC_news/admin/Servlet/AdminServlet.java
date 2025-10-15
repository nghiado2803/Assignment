package ABC_news.admin.Servlet;

import java.io.IOException;
import java.util.List;

import ABC_news.DAO.NewsDAO;
import ABC_news.DAO.NewsDAOImpl;
import ABC_news.DAO.UserDAO;
import ABC_news.DAO.UserDAOImpl;
import ABC_news.Entity.News;
import ABC_news.Entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	NewsDAO newsDAO = new NewsDAOImpl();
    	UserDAO userDAO = new UserDAOImpl();
        List<User> users = userDAO.findAll();
        request.setAttribute("users", users);
        
        List<News> homeLeft = newsDAO.findHomeByPosition(1);   // vị trí cột trái
        List<News> homeCenter = newsDAO.findHomeByPosition(2); 
        
        request.setAttribute("homeLeft", homeLeft);
        request.setAttribute("homeCenter", homeCenter);
        
        request.getRequestDispatcher("/admin/admin.jsp").forward(request, response);
    }
}

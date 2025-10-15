package ABC_news.admin.Servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import ABC_news.DAO.NewsDAO;
import ABC_news.DAO.NewsDAOImpl;
import ABC_news.Entity.News;

@WebServlet("/admin/manage_all_news")
public class ManageAllNewsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	NewsDAO newsDAO = new NewsDAOImpl();
        List<News> newsList = newsDAO.findAll();
        req.setAttribute("newsList", newsList);
        req.getRequestDispatcher("/admin/manage_all_news.jsp").forward(req, resp);
    }
}

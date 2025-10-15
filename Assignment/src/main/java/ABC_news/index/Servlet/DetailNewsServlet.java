package ABC_news.index.Servlet;

import ABC_news.DAO.NewsDAO;
import ABC_news.DAO.NewsDAOImpl;
import ABC_news.Entity.News;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/detail")
public class DetailNewsServlet extends HttpServlet {

    private NewsDAO newsDAO;

    @Override
    public void init() throws ServletException {
        newsDAO = new NewsDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");

        if (id == null || id.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/index");
            return;
        }

        News news = newsDAO.getNewsById(id);
        if (news == null) {
            response.sendRedirect(request.getContextPath() + "/index");
            return;
        }

        request.setAttribute("news", news);
        request.getRequestDispatcher("/views/news_detail.jsp").forward(request, response);
    }
}

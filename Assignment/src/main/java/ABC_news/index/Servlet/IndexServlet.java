package ABC_news.index.Servlet;

import ABC_news.DAO.CategoryDAO;
import ABC_news.DAO.CategoryDAOImpl;
import ABC_news.DAO.NewsDAO;
import ABC_news.DAO.NewsDAOImpl;
import ABC_news.Entity.Category;
import ABC_news.Entity.News;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println(">>> Đã chạy vào IndexServlet!");

        NewsDAO newsDAO = new NewsDAOImpl();
        CategoryDAO categoryDAO = new CategoryDAOImpl();

        List<News> topViewed = newsDAO.findTopViewed(5);
        List<News> latestNews = newsDAO.findLatest(5);
        List<Category> categories = categoryDAO.findAll();

        for (Category cat : categories) {
            List<News> approvedNews = newsDAO.findApprovedByCategory(cat.getId());
            cat.setNewsList(approvedNews);
        }

        List<News> homeLeft = newsDAO.findHomeByPosition(1);   
        List<News> homeCenter = newsDAO.findHomeByPosition(2); 

        request.setAttribute("topViewed", topViewed);
        request.setAttribute("latestNews", latestNews);
        request.setAttribute("categories", categories);
        request.setAttribute("homeLeft", homeLeft);
        request.setAttribute("homeCenter", homeCenter);
        

        request.getRequestDispatcher("/index/index.jsp").forward(request, response);
    }
}

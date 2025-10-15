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
import java.util.List;

@WebServlet("/category")
public class CategoryServlet extends HttpServlet {

    private NewsDAO newsDAO = new NewsDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	NewsDAO newsDAO = new NewsDAOImpl();
        List<News> topViewed = newsDAO.findTopViewed(5);
        List<News> latestNews = newsDAO.findLatest(5);

        request.setAttribute("topViewed", topViewed);
        request.setAttribute("latestNews", latestNews);

        System.out.println("[LOG] TopViewed size: " + topViewed.size());
        for (News n : topViewed) System.out.println("TopViewed: " + n.getTitle());
        System.out.println("[LOG] LatestNews size: " + latestNews.size());
        for (News n : latestNews) System.out.println("LatestNews: " + n.getTitle());
        String categoryName = request.getParameter("name");
        if (categoryName == null || categoryName.trim().isEmpty()) {
            categoryName = "Văn hóa"; 
        }

        List<News> newsList = newsDAO.getApprovedNewsByCategoryName(categoryName);

        request.setAttribute("categoryName", categoryName);
        request.setAttribute("newsList", newsList);

        request.getRequestDispatcher("/index/news_category.jsp").forward(request, response);
    }
}

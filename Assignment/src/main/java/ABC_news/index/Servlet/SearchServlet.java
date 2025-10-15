package ABC_news.index.Servlet;

import ABC_news.DAO.NewsDAOImpl;
import ABC_news.Entity.News;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String keyword = request.getParameter("keyword");

        if (keyword == null || keyword.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/index/index.jsp");
            return;
        }

        NewsDAOImpl dao = new NewsDAOImpl();
        List<News> results = dao.searchNews(keyword);

        request.setAttribute("searchResults", results);
        request.setAttribute("keyword", keyword);

        request.getRequestDispatcher("/index/search_result.jsp").forward(request, response);

        System.out.println(">>> Keyword: " + keyword + " | Kết quả: " + results.size());
    }
}

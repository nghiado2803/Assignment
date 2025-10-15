package ABC_news.reporter.Servlet;

import ABC_news.DAO.NewsDAO;
import ABC_news.DAO.NewsDAOImpl;
import ABC_news.Entity.News;
import ABC_news.Entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/reporter")
public class ReporterServlet extends HttpServlet {
    private NewsDAO newsDao = new NewsDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            System.out.println("[LOG] Không có user trong session!");
            resp.sendRedirect(req.getContextPath() + "/index/index.jsp");
            return;
        }
        System.out.println("[LOG] User trong session: " + user.getId() + " - " + user.getEmail());

        List<News> list = newsDao.selectByAuthor(user.getId());
        System.out.println("[LOG] Số lượng tin lấy được: " + list.size());
        for (News n : list) {
            System.out.println("[LOG] NewsId: " + n.getId() + ", Title: " + n.getTitle() + ", Author: " + n.getAuthor());
        }

        NewsDAO newsDAO = new NewsDAOImpl();
        List<News> topViewed = newsDAO.findTopViewed(5);
        List<News> latestNews = newsDAO.findLatest(5);

        req.setAttribute("topViewed", topViewed);
        req.setAttribute("latestNews", latestNews);

        System.out.println("[LOG] TopViewed size: " + topViewed.size());
        for (News n : topViewed) System.out.println("TopViewed: " + n.getTitle());
        System.out.println("[LOG] LatestNews size: " + latestNews.size());
        for (News n : latestNews) System.out.println("LatestNews: " + n.getTitle());

        req.setAttribute("newsList", list);
        req.setAttribute("fullname", user.getFullname());
        List<News> homeLeft = newsDAO.findHomeByPosition(1);

        req.setAttribute("homeLeft", homeLeft);

        req.getRequestDispatcher("/reporter/reporter.jsp").forward(req, resp);

    }
}

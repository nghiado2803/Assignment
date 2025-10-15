package ABC_news.reporter.Servlet;

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

@WebServlet("/add_edit_news")
public class AddEditNewsServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {

	    CategoryDAO categoryDAO = new CategoryDAOImpl();
	    List<Category> categories = categoryDAO.findAll();
	    System.out.println(">>> Có " + categories.size() + " thể loại");
	    req.setAttribute("categories", categories);

	    String id = req.getParameter("id");
	    if (id != null && !id.isEmpty()) {
	        System.out.println(">>> Đang sửa tin có id = " + id);
	        NewsDAOImpl newsDAO = new NewsDAOImpl();
	        News news = newsDAO.findById(id);

	        if (news != null) {
	            System.out.println(">>> Tìm thấy tin: " + news.getTitle());
	            req.setAttribute("news", news);
	        } else {
	            System.out.println(">>> Không tìm thấy tin với id này!");
	        }
	    }

	    req.getRequestDispatcher("/reporter/edit_news.jsp").forward(req, resp);
	}

}

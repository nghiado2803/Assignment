package ABC_news.Servlet.admin;

import ABC_news.DAO.NewsletterDAO;
import ABC_news.DAO.NewsletterDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/newsletter")
public class NewsLetterServlet extends HttpServlet {
    private NewsletterDAO newsletterDAO = new NewsletterDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> emails = newsletterDAO.getActiveEmails();
        request.setAttribute("emails", emails);

        String deleteEmail = request.getParameter("delete");
        if (deleteEmail != null) {
            newsletterDAO.deleteSubscriber(deleteEmail);
            response.sendRedirect(request.getContextPath() + "/admin/newsletter");
            return;
        }

        request.getRequestDispatcher("/admin/newsletter.jsp").forward(request, response);
    }
}
package ABC_news.Servlet.admin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import ABC_news.DAO.NewsDAO;
import ABC_news.DAO.NewsDAOImpl;
import ABC_news.DAO.UserDAO;
import ABC_news.DAO.UserDAOImpl;
import ABC_news.DAO.NewsletterDAO;
import ABC_news.DAO.NewsletterDAOImpl;
import ABC_news.Entity.News;
import ABC_news.Entity.User;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({
    "/admin", 
    "/admin/approve_news", 
    "/admin/send_email",
    "/admin/feature_news"  
})
public class AdminServlet extends HttpServlet {

    private NewsDAO newsDAO;
    private UserDAO userDAO;
    private NewsletterDAO newsletterDAO;

    @Override
    public void init() throws ServletException {
        newsDAO = new NewsDAOImpl();
        userDAO = new UserDAOImpl();
        newsletterDAO = new NewsletterDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String uri = request.getRequestURI();

        if (uri.endsWith("/admin")) {
            showAdminDashboard(request, response);
        } 
        else if (uri.endsWith("/approve_news")) {
            approveNews(request, response);
        } 
        else if (uri.endsWith("/send_email")) {
            sendEmail(request, response);
        } 
        else if (uri.endsWith("/feature_news")) {   // ✅ thêm xử lý cho feature
            featureNews(request, response);
        }
    }

    private void showAdminDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> users = userDAO.findAll();
        request.setAttribute("users", users);

        List<News> homeLeft = newsDAO.findHomeByPosition(1);
        List<News> homeCenter = newsDAO.findHomeByPosition(2);

        request.setAttribute("homeLeft", homeLeft);
        request.setAttribute("homeCenter", homeCenter);

        request.getRequestDispatcher("/admin/admin.jsp").forward(request, response);
    }

    private void approveNews(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String id = request.getParameter("id");
        if (id == null || id.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?error=missing_id");
            return;
        }

        try {
            boolean statusUpdated = newsDAO.updateStatus(id, "Đã duyệt");
            newsDAO.updateApproved(id, true);

            if (statusUpdated) {
                response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?success=approved");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?error=update_failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?error=exception");
        }
    }

    private void sendEmail(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String newsId = request.getParameter("id");
        if (newsId == null || newsId.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?error=missing_id");
            return;
        }

        News news = newsDAO.getById(newsId);
        if (news == null) {
            response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?error=not_found");
            return;
        }

        if (!"Đã duyệt".equalsIgnoreCase(news.getStatus())) {
            response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?error=not_approved");
            return;
        }

        List<String> recipients = newsletterDAO.getActiveEmails();
        if (recipients == null || recipients.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?msg=no_subscribers");
            return;
        }

        try {
            sendBulkEmail(recipients, news.getTitle(), news.getContent());
            newsDAO.updateEmailed(newsId, true);
            response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?success=email_sent");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?error=email_failed");
        }
    }

    private void featureNews(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String id = request.getParameter("id");
        if (id == null || id.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?error=missing_id");
            return;
        }

        News news = newsDAO.getNewsById(id);
        if (news == null) {
            response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?error=not_found");
            return;
        }

        if (!"Đã duyệt".equalsIgnoreCase(news.getStatus())) {
            response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?error=not_approved");
            return;
        }

        try {
            news.setHome(true);
            newsDAO.update(news);
            newsDAO.updateFeature(id, true);

            response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?success=featured");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?error=exception");
        }
    }

    private void sendBulkEmail(List<String> toList, String subject, String content)
            throws MessagingException, UnsupportedEncodingException {

        final String FROM_EMAIL = "nghiadvpy00194@gmail.com";
        final String APP_PASSWORD = "kmgd lwlf hjxj roqt";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        });

        for (String to : toList) {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL, "ABC News", "UTF-8"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("[ABC News] " + subject);
            message.setContent(
                    "<h2>" + subject + "</h2>" +
                            "<p>" + content + "</p>" +
                            "<hr><small>Cảm ơn bạn đã theo dõi bản tin của <b>ABC News</b>.</small>",
                    "text/html; charset=UTF-8"
            );
            Transport.send(message);
        }
    }
}

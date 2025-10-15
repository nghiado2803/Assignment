package ABC_news.admin.Servlet;

import ABC_news.DAO.NewsDAO;
import ABC_news.DAO.NewsDAOImpl;
import ABC_news.DAO.NewsletterDAO;
import ABC_news.DAO.NewsletterDAOImpl;
import ABC_news.Entity.News;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

@WebServlet("/admin/send_email")
public class SendEmailServlet extends HttpServlet {

    // 🔹 Cấu hình tài khoản gửi email
    private static final String FROM_EMAIL = "nghiadvpy00194@gmail.com";
    private static final String APP_PASSWORD = "kmgd lwlf hjxj roqt";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String newsId = request.getParameter("id");
        if (newsId == null || newsId.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?error=missing_id");
            return;
        }

        NewsDAO newsDAO = new NewsDAOImpl();
        NewsletterDAO newsletterDAO = new NewsletterDAOImpl();

        // 🔹 Lấy thông tin bài viết
        News news = newsDAO.getById(newsId);
        if (news == null) {
            response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?error=not_found");
            return;
        }

        // ✅ Kiểm tra bài viết đã được duyệt chưa
        if (!"Đã duyệt".equalsIgnoreCase(news.getStatus())) {
            response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?error=not_approved");
            return;
        }

        // 🔹 Lấy danh sách email người đăng ký đang hoạt động
        List<String> recipients = newsletterDAO.getActiveEmails();
        if (recipients == null || recipients.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?msg=no_subscribers");
            return;
        }

        // 🔹 Gửi email hàng loạt
        try {
            sendBulkEmail(recipients, news.getTitle(), news.getContent());

            // ✅ Sau khi gửi xong, cập nhật trạng thái emailed = true
            newsDAO.updateEmailed(newsId, true);

            response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?success=email_sent");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/manage_all_news?error=email_failed");
        }
    }

    /**
     * Gửi email hàng loạt đến danh sách người đăng ký
     */
    private void sendBulkEmail(List<String> toList, String subject, String content)
            throws MessagingException, UnsupportedEncodingException {

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

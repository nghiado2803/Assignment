package ABC_news.index.Servlet;

import ABC_news.DAO.NewsletterDAO;
import ABC_news.DAO.NewsletterDAOImpl;
import ABC_news.Entity.Newsletter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/subscribe")
public class SubscribeServlet extends HttpServlet {
    private NewsletterDAO newsletterDAO = new NewsletterDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String email = req.getParameter("email");

        if (email == null || email.trim().isEmpty()) {
            resp.getWriter().write("<script>alert('Vui lòng nhập email hợp lệ!'); history.back();</script>");
            return;
        }

        if (newsletterDAO.exists(email)) {
            resp.getWriter().write("<script>alert('Email này đã đăng ký nhận bản tin rồi!'); history.back();</script>");
            return;
        }

        boolean success = newsletterDAO.addSubscriber(email);

        if (success) {
            resp.getWriter().write("<script>alert('Đăng ký nhận bản tin thành công!'); history.back();</script>");
        } else {
            resp.getWriter().write("<script>alert('Đăng ký thất bại! Vui lòng thử lại sau.'); history.back();</script>");
        }
    }
}

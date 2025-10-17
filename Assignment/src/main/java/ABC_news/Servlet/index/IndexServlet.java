package ABC_news.Servlet.index;

import ABC_news.DAO.*;
import ABC_news.Entity.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet({
    "/index",
    "/category",
    "/detail",
    "/search",
    "/login",
    "/logout",
    "/changePassword",
    "/subscribe"
})
public class IndexServlet extends HttpServlet {

    private NewsDAO newsDAO;
    private CategoryDAO categoryDAO;
    private UserDAO userDAO;
    private NewsletterDAO newsletterDAO;

    @Override
    public void init() throws ServletException {
        newsDAO = new NewsDAOImpl();
        categoryDAO = new CategoryDAOImpl();
        userDAO = new UserDAOImpl();
        newsletterDAO = new NewsletterDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String uri = req.getRequestURI();

        if (uri.endsWith("/index")) {
            showHome(req, resp);
        } 
        else if (uri.endsWith("/category")) {
            showCategory(req, resp);
        } 
        else if (uri.endsWith("/detail")) {
            showDetail(req, resp);
        } 
        else if (uri.endsWith("/search")) {
            doSearch(req, resp);
        } 
        else if (uri.endsWith("/logout")) {
            doLogout(req, resp);
        } 
        else {
            resp.sendRedirect(req.getContextPath() + "/index");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String uri = req.getRequestURI();

        if (uri.endsWith("/login")) {
            doLogin(req, resp);
        } 
        else if (uri.endsWith("/changePassword")) {
            changePassword(req, resp);
        } 
        else if (uri.endsWith("/subscribe")) {
            doSubscribe(req, resp);
        } 
        else {
            resp.sendRedirect(req.getContextPath() + "/index");
        }
    }

    private void showHome(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<News> topViewed = newsDAO.findTopViewedApproved(5);
        List<News> latestNews = newsDAO.findLatestApproved(5);
        List<Category> categories = categoryDAO.findAll();

        for (Category cat : categories) {
            List<News> approvedNews = newsDAO.findApprovedByCategory(cat.getId());
            cat.setNewsList(approvedNews);
        }

        req.setAttribute("topViewed", topViewed);
        req.setAttribute("latestNews", latestNews);
        req.setAttribute("categories", categories);
        req.setAttribute("homeLeft", newsDAO.findHomeByPosition(1));
        req.setAttribute("homeCenter", newsDAO.findHomeByPosition(2));

        req.getRequestDispatcher("/index/index.jsp").forward(req, resp);
    }

    private void showCategory(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String categoryName = req.getParameter("name");
        if (categoryName == null || categoryName.trim().isEmpty()) {
            categoryName = "Văn hóa";
        }

        List<News> newsList = newsDAO.getApprovedNewsByCategoryName(categoryName);
        List<News> topViewed = newsDAO.findTopViewedApproved(5);
        List<News> latestNews = newsDAO.findLatestApproved(5);

        req.setAttribute("categoryName", categoryName);
        req.setAttribute("newsList", newsList);
        req.setAttribute("topViewed", topViewed);
        req.setAttribute("latestNews", latestNews);

        req.getRequestDispatcher("/index/news_category.jsp").forward(req, resp);
    }

    private void showDetail(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null || id.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/index");
            return;
        }

        newsDAO.incrementViewCount(id);

        News news = newsDAO.getNewsById(id);
        if (news == null) {
            resp.sendRedirect(req.getContextPath() + "/index");
            return;
        }

        List<News> topViewed = newsDAO.findTopViewedApproved(5);
        List<News> latestNews = newsDAO.findLatestApproved(5);

        req.setAttribute("news", news);
        req.setAttribute("topViewed", topViewed);
        req.setAttribute("latestNews", latestNews);

        req.getRequestDispatcher("/views/news_detail.jsp").forward(req, resp);
    }

    private void doSearch(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        if (keyword == null || keyword.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/index");
            return;
        }

        List<News> results = newsDAO.searchNews(keyword);
        List<News> topViewed = newsDAO.findTopViewedApproved(5);
        List<News> latestNews = newsDAO.findLatestApproved(5);

        req.setAttribute("searchResults", results);
        req.setAttribute("keyword", keyword);
        req.setAttribute("topViewed", topViewed);
        req.setAttribute("latestNews", latestNews);

        req.getRequestDispatcher("/index/search_result.jsp").forward(req, resp);
    }

    private void doLogin(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String redirectURL = req.getParameter("redirectURL");

        User user = userDAO.login(email, password);
        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            if (user.isRole()) {
                resp.sendRedirect(req.getContextPath() + "/admin");
            } else {
                resp.sendRedirect(req.getContextPath() + "/reporter");
            }
        } else {
            if (redirectURL == null || redirectURL.isEmpty()) {
                redirectURL = "/index";
            }
            resp.sendRedirect(req.getContextPath() + redirectURL + "?error=1");
        }
    }

    private void doLogout(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) session.invalidate();
        resp.sendRedirect(req.getContextPath() + "/index");
    }

    private void changePassword(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.getWriter().write("<script>alert('Phiên đăng nhập đã hết hạn!'); window.location='/index';</script>");
            return;
        }

        String oldPass = req.getParameter("oldPassword");
        String newPass = req.getParameter("newPassword");
        String confirm = req.getParameter("confirmPassword");

        if (!user.getPassword().equals(oldPass)) {
            resp.getWriter().write("<script>alert('Mật khẩu cũ không đúng!'); history.back();</script>");
            return;
        }

        if (!newPass.equals(confirm)) {
            resp.getWriter().write("<script>alert('Xác nhận mật khẩu không khớp!'); history.back();</script>");
            return;
        }

        user.setPassword(newPass);
        boolean success = userDAO.updatePassword(user.getId(), newPass);

        if (success) {
            session.setAttribute("user", user);
            String redirectUrl = user.isRole() ? "admin/admin.jsp" : "reporter/reporter.jsp";
            resp.getWriter().write("<script>alert('Đổi mật khẩu thành công!'); window.location='" + redirectUrl + "';</script>");
        } else {
            resp.getWriter().write("<script>alert('Lỗi khi đổi mật khẩu! Vui lòng thử lại.'); history.back();</script>");
        }
    }

    private void doSubscribe(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        String email = req.getParameter("email");

        if (email == null || email.trim().isEmpty()) {
            resp.getWriter().write("<script>alert('Vui lòng nhập email hợp lệ!'); history.back();</script>");
            return;
        }

        if (newsletterDAO.exists(email)) {
            resp.getWriter().write("<script>alert('Email này đã đăng ký rồi!'); history.back();</script>");
            return;
        }

        boolean success = newsletterDAO.addSubscriber(email);
        if (success) {
            resp.getWriter().write("<script>alert('Đăng ký thành công!'); history.back();</script>");
        } else {
            resp.getWriter().write("<script>alert('Đăng ký thất bại!'); history.back();</script>");
        }
    }
}

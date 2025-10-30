<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="ABC_news.Entity.User" %>
<%
    User user = (User) session.getAttribute("user");
    String fullname = (user != null && user.getFullname() != null)
            ? user.getFullname() : "Quản trị viên";
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="ABC News - Quản lý loại tin">
    <title>ABC News - Quản lý loại tin</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<header class="site-header">
    <div class="container">
        <div class="logo">ABC <span>News</span></div>
        <nav class="menu">
            <a href="${pageContext.request.contextPath}/index"
               class="${fn:contains(pageContext.request.requestURI, '/index') ? 'active' : ''}">Trang chủ</a>
            <a href="${pageContext.request.contextPath}/category?name=Văn hóa"
               class="${fn:contains(pageContext.request.requestURI, 'Văn hóa') ? 'active' : ''}">Văn hóa</a>
            <a href="${pageContext.request.contextPath}/category?name=Pháp luật"
               class="${fn:contains(pageContext.request.requestURI, 'Pháp luật') ? 'active' : ''}">Pháp luật</a>
            <a href="${pageContext.request.contextPath}/category?name=Thể thao"
               class="${fn:contains(pageContext.request.requestURI, 'Thể thao') ? 'active' : ''}">Thể thao</a>
            <a href="${pageContext.request.contextPath}/admin"
               class="${fn:contains(pageContext.request.requestURI, '/admin') ? 'active' : ''}">Quản trị</a>
        </nav>

        <div class="header-actions">
            Xin chào <strong><%= fullname %></strong>
            <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Đăng xuất</a>
        </div>
    </div>
</header>

<div class="container">
     <section class="center-col">
        <h2>Danh sách email đăng ký nhận tin</h2>
        <table class="news-table">
            <thead>
            <tr>
                <th>STT</th>
                <th>Email</th>
                <th>Hành động</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="email" items="${emails}" varStatus="status">
                <tr>
                    <td>${status.index + 1}</td>
                    <td>${email}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/newsletter?delete=${email}"
                           class="delete-btn"
                           onclick="return confirm('Bạn có chắc muốn xóa email này?')">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>
</div>





<%@ include file="../includes/news_index_footer.jsp" %>


</body>
</html>

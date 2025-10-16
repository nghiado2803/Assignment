<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page import="ABC_news.Entity.User" %>
<%
    User user = (User) session.getAttribute("user");
    String fullname = (user != null && user.getFullname() != null) ? user.getFullname() : "Quản trị viên";
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="ABC News - Quản lý tất cả tin tức.">
    <meta name="keywords" content="quản lý tin tức, quản trị, ABC News">
    <meta name="author" content="ABC News">
    <title>ABC News - Quản lý tất cả tin tức</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&family=Open+Sans:wght@400;600&display=swap">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
.action-btn {
    display: inline-block;
    padding: 8px 16px;
    font-size: 14px;
    font-weight: 600;
    border-radius: 6px;
    text-decoration: none;
    color: #fff;
    line-height: 1;
    margin: 2px;
    transition: all 0.2s ease-in-out;
}

.edit-btn {
    background-color: #3498db; 
}
.edit-btn:hover {
    background-color: #2980b9;
}

.delete-btn {
    background-color: #e74c3c; 
}
.delete-btn:hover {
    background-color: #c0392b;
}

.feature-btn {
    background-color: #f39c12; 
}
.feature-btn:hover {
    background-color: #e67e22;
}

.email-btn {
    background-color: #9b59b6; 
}
.email-btn:hover {
    background-color: #8e44ad;
}

.approve-btn {
    background-color: #27ae60; 
}
.approve-btn:hover {
    background-color: #2ecc71;
}

.action-btn.disabled,
.approve-btn.disabled,
.feature-btn.disabled,
.email-btn.disabled {
    background-color: #95a5a6;
    cursor: not-allowed;
}

      
</style>
    
</head>
<body>


<!-- Header -->
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
    Xin chào <strong><%= user.getFullname() != null ? user.getFullname() : "Admin" %></strong>
    <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Đăng xuất</a>
</div>
    </div>
</header>

<div class="container">
    <section class="center-col">
        <h2>Quản lý tất cả tin tức</h2>
        <div class="action-bar">
                    <a href="${pageContext.request.contextPath}/add_edit_news" class="add-news-btn">Thêm tin mới</a>
        </div>

        <table class="news-table">
            <thead>
                <tr>
                    <th>Tiêu đề</th>
                    <th>Loại tin</th>
                    <th>Phóng viên</th>
                    <th>Ngày đăng</th>
                    <th>Trạng thái</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
<c:forEach var="n" items="${newsList}">
    <tr>
        <td>${n.title}</td>
        <td>${n.categoryName}</td>
		<td>${n.authorName}</td>
        <td>${n.postedDate}</td>
        <td>${n.status}</td>
     <td>
	<a href="${pageContext.request.contextPath}/admin/add_edit_news?id=${n.id}" class="action-btn edit-btn">Sửa</a>
    <a href="delete_news?id=${n.id}" class="action-btn delete-btn"
       onclick="return confirm('Bạn có chắc muốn xóa?')">Xóa</a>

    <c:if test="${!n.home}">
        <a href="feature_news?id=${n.id}" class="action-btn feature-btn">Đưa lên trang chủ</a>
    </c:if>

    <c:if test="${n.status eq 'Đã duyệt' && !n.emailed}">
        <a href="send_email?id=${n.id}" class="action-btn email-btn">Gửi email</a>
    </c:if>

    <c:if test="${n.status ne 'Đã duyệt'}">
        <a href="approve_news?id=${n.id}" class="action-btn approve-btn">Duyệt</a>
    </c:if>
</td>


    </tr>
</c:forEach>
</tbody>


        </table>
    </section>
</div>

<!-- Footer -->
<%@ include file="../includes/news_index_footer.jsp" %>
</body>
</html>

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
        <h2>Quản lý loại tin</h2>
        <div class="action-bar">
            <a href="#" class="add-news-btn" onclick="showModal('add-category-modal')">+ Thêm loại tin</a>
        </div>

        <table class="news-table">
            <thead>
            <tr>
                <th>Mã loại tin</th>
                <th>Tên loại tin</th>
                <th>Hành động</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="c" items="${categories}">
                <tr>
                    <td>${c.id}</td>
                    <td>${c.name}</td>
                    <td>
                        <a href="#" class="edit-btn"
                           onclick="openEditModal('${c.id}', '${c.name}')">Sửa</a>
                        <a href="${pageContext.request.contextPath}/admin/delete_category?id=${c.id}"
                           class="delete-btn"
                           onclick="return confirm('Bạn có chắc muốn xóa loại tin này?')">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>
</div>

<!-- Modal thêm loại tin -->
<div id="add-category-modal" class="modal">
    <div class="modal-content">
        <span class="close-btn" onclick="closeModal('add-category-modal')">&times;</span>
        <h2>Thêm loại tin</h2>
        <form action="${pageContext.request.contextPath}/admin/add_category" method="post">
            <div class="form-group">
                <label for="categoryName">Tên loại tin</label>
                <input type="text" id="categoryName" name="categoryName" placeholder="Nhập tên loại tin" required>
            </div>
            <button type="submit" class="submit-btn">Thêm</button>
        </form>
    </div>
</div>

<!-- Modal sửa loại tin -->
<div id="edit-category-modal" class="modal">
    <div class="modal-content">
        <span class="close-btn" onclick="closeModal('edit-category-modal')">&times;</span>
        <h2>Sửa loại tin</h2>
        <form action="${pageContext.request.contextPath}/admin/edit_category" method="post">
            <input type="hidden" id="edit-id" name="categoryId">
            <div class="form-group">
                <label for="edit-name">Tên loại tin</label>
                <input type="text" id="edit-name" name="categoryName" required>
            </div>
            <button type="submit" class="submit-btn">Lưu thay đổi</button>
        </form>
    </div>
</div>

<%@ include file="../includes/news_index_footer.jsp" %>

<script>
    function showModal(id) {
        document.getElementById(id).style.display = 'flex';
    }

    function closeModal(id) {
        document.getElementById(id).style.display = 'none';
    }

    function openEditModal(id, name) {
        document.getElementById('edit-id').value = id;
        document.getElementById('edit-name').value = name;
        showModal('edit-category-modal');
    }
</script>

</body>
</html>

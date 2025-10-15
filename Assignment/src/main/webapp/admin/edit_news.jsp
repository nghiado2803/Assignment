<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="ABC_news.Entity.User" %>

<%
    User user = (User) session.getAttribute("user");
    String fullname = (user != null && user.getFullname() != null)
            ? user.getFullname() : "Người dùng";
    boolean isAdmin = (user != null && user.isRole());
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="ABC News - Trang thêm/sửa tin tức.">
    <meta name="author" content="ABC News">
    <title>
        <c:choose>
            <c:when test="${news != null}">Sửa Tin Tức</c:when>
            <c:otherwise>Thêm Tin Tức Mới</c:otherwise>
        </c:choose>
        - ABC News
    </title>

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

            <c:choose>
                <c:when test="<%= isAdmin %>">
                    <a href="${pageContext.request.contextPath}/admin/manage_all_news"
                       class="${fn:contains(pageContext.request.requestURI, '/admin') ? 'active' : ''}">
                        Quản trị
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/reporter/manage_my_news"
                       class="${fn:contains(pageContext.request.requestURI, '/reporter') ? 'active' : ''}">
                        Quản lý tin
                    </a>
                </c:otherwise>
            </c:choose>
        </nav>

        <div class="header-actions">
            <span class="user-info">Xin chào, <strong><%= fullname %></strong></span>
            <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Đăng xuất</a>
        </div>
    </div>
</header>

<div class="container">
    <section class="center-col">
        <h2>
            <c:choose>
                <c:when test="${news != null}">Sửa Tin Tức</c:when>
                <c:otherwise>Thêm Tin Tức Mới</c:otherwise>
            </c:choose>
        </h2>

        <form action="${pageContext.request.contextPath}/admin/add_edit_news"
              method="post"
              enctype="multipart/form-data"
              class="news-form">

            <input type="hidden" name="id" value="${news.id}">

            <div class="form-group">
                <label for="title">Tiêu đề</label>
                <input type="text" id="title" name="title"
                       value="${news.title}" placeholder="Nhập tiêu đề tin" required>
            </div>

            <div class="form-group">
                <label for="category">Thể loại</label>
                <select name="categoryId" id="category" required>
                    <option value="">-- Chọn thể loại --</option>
                    <c:forEach var="c" items="${categories}">
                        <option value="${c.id}"
                            <c:if test="${news != null && news.categoryId == c.id}">selected</c:if>>
                            ${c.name}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="image">Hình ảnh</label>
                <input type="file" id="image" name="image" accept="image/*"
                       class="form-control"
                       <c:if test="${news == null}">required</c:if>>

                <c:if test="${news != null && news.image != null}">
                    <input type="hidden" name="oldImage" value="${news.image}">
                    <div style="margin-top:10px;">
                        <p>Ảnh hiện tại:</p>
                        <img src="${pageContext.request.contextPath}/uploads/${news.image}"
                             alt="Ảnh tin tức" width="150"
                             style="border-radius:8px; box-shadow:0 0 5px rgba(0,0,0,0.2);">
                    </div>
                </c:if>
            </div>

            <!-- Nội dung -->
            <div class="form-group">
                <label for="content">Nội dung</label>
                <textarea id="content" name="content" rows="6"
                          placeholder="Nhập nội dung tin" required>${news.content}</textarea>
            </div>

            <!-- Ngày đăng -->
            <div class="form-group">
                <label for="postedDate">Ngày đăng</label>
                <input type="date" id="postedDate" name="postedDate"
                       value="${news.postedDate}" required>
            </div>

            <!-- Tác giả -->
            <div class="form-group">
                <label for="author">Tác giả</label>
                <input type="text" id="author" value="<%= fullname %>" readonly>
            </div>

            <!-- Vị trí hiển thị -->
            <div class="form-group">
                <label for="position">Vị trí hiển thị</label>
                <input type="number" id="position" name="position" min="1"
                       value="${news.position}" placeholder="Nhập thứ tự hiển thị (VD: 1, 2, 3)">
            </div>

            <!-- View Count (ẩn) -->
            <input type="hidden" name="viewCount"
                   value="${news.viewCount != null ? news.viewCount : 0}">

            <!-- Nút lưu -->
            <button type="submit" class="submit-btn">
                <c:choose>
                    <c:when test="${news != null}">Cập nhật tin</c:when>
                    <c:otherwise>Lưu tin mới</c:otherwise>
                </c:choose>
            </button>
        </form>
    </section>
</div>

<!-- FOOTER -->
<%@ include file="../includes/news_index_footer.jsp" %>

</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="ABC_news.Entity.User" %>
<%@ page import="ABC_news.DAO.CategoryDAOImpl" %>
<%@ page import="ABC_news.Entity.Category" %>
<%@ page import="java.util.List" %>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

<%
    User user = (User) session.getAttribute("user");
    boolean isLoggedIn = (user != null);
    boolean isAdmin = false;
    if (isLoggedIn) {
        isAdmin = user.isRole(); 
    }

    CategoryDAOImpl dao = new CategoryDAOImpl();
    List<Category> navCategories = dao.findAll();
    request.setAttribute("navCategories", navCategories);
%>

<nav class="menu">
    <a href="${pageContext.request.contextPath}/index"
       class="${fn:endsWith(pageContext.request.requestURI, '/index') ? 'active' : ''}">
       Trang chủ
    </a>

    <c:forEach var="cat" items="${navCategories}">
        <a href="${pageContext.request.contextPath}/category?name=${cat.name}"
           class="${fn:contains(pageContext.request.queryString, cat.name) ? 'active' : ''}">
            ${cat.name}
        </a>
    </c:forEach>

    <c:if test="${not empty sessionScope.user}">
        <c:choose>
            <c:when test="${sessionScope.user.role}">
                <a href="${pageContext.request.contextPath}/admin"
                   class="${fn:contains(pageContext.request.requestURI, '/admin') ? 'active' : ''}">
                    Quản trị
                </a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/reporter"
                   class="${fn:contains(pageContext.request.requestURI, '/reporter') ? 'active' : ''}">
                    Quản lý tin
                </a>
            </c:otherwise>
        </c:choose>
    </c:if>
</nav>

<div class="header-actions">
    <!-- Ô tìm kiếm -->
    <form action="${pageContext.request.contextPath}/search" method="get" class="search-form">
        <input type="text" name="keyword" placeholder="Tìm kiếm tin tức..." class="search-bar" required>
        <button type="submit" class="search-btn">
            <i class="fa fa-search"></i>
        </button>
    </form>

    <% if (!isLoggedIn) { %>
        <button class="login-btn" onclick="showModal('login-modal')">Đăng nhập</button>
    <% } else { %>
        <span class="user-info">Xin chào <strong><%= user.getFullname() %></strong></span>
        <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Đăng xuất</a>
    <% } %>
</div>

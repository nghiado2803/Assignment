<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Kết quả tìm kiếm tin tức - ABC News">
    <meta name="keywords" content="tin tức, kết quả tìm kiếm, ABC News">
    <meta name="author" content="ABC News">
    <title>Kết quả tìm kiếm - ABC News</title>
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&family=Open+Sans:wght@400;600&display=swap">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<!-- Header -->
<header class="site-header">
        <div class="container">
            <div class="logo">ABC <span>News</span></div>
            <%@ page import="ABC_news.Entity.User" %>
<%
    User user = (User) session.getAttribute("user");
    boolean isLoggedIn = (user != null);
    boolean isAdmin = false;
    if (isLoggedIn) {
        isAdmin = user.isRole(); 
    }
%>
<nav class="menu">
    <a href="${pageContext.request.contextPath}/index"
       class="${fn:endsWith(pageContext.request.requestURI, '/index') ? 'active' : ''}">Trang chủ</a>

    <a href="${pageContext.request.contextPath}/category?name=Văn hóa"
       class="${fn:contains(pageContext.request.queryString, 'Văn hóa') ? 'active' : ''}">Văn hóa</a>

    <a href="${pageContext.request.contextPath}/category?name=Pháp luật"
       class="${fn:contains(pageContext.request.queryString, 'Pháp luật') ? 'active' : ''}">Pháp luật</a>

    <a href="${pageContext.request.contextPath}/category?name=Thể thao"
       class="${fn:contains(pageContext.request.queryString, 'Thể thao') ? 'active' : ''}">Thể thao</a>

    <c:if test="${not empty sessionScope.user}">
        <c:choose>
            <c:when test="${sessionScope.user.role}">
                <a href="${pageContext.request.contextPath}/admin">Quản trị</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/reporter">Quản lý tin</a>
            </c:otherwise>
        </c:choose>
    </c:if>
</nav>



		
		<div class="header-actions">
		    	<form action="${pageContext.request.contextPath}/search" method="get" class="search-form">
				    <input type="text" name="keyword" placeholder="Tìm kiếm tin tức..." class="search-bar" required>
				    <button type="submit" class="search-btn">🔍</button>
				</form>
		</div>
		
		
    </header>

<!-- Main Content -->
<div class="container">
    <div class="container-2col">

        <!-- Left Column: Search Results -->
        <section class="left-col">
            <h2>Kết quả tìm kiếm cho: "<c:out value='${keyword}'/>"</h2>
            <hr>

            <c:if test="${empty searchResults}">
                <p>Không tìm thấy kết quả nào phù hợp.</p>
            </c:if>

            <c:forEach var="news" items="${searchResults}">
                <article class="news-item">
			   <img src="${pageContext.request.contextPath}/uploads/${news.image}" alt="Hình ảnh tin tức" class="news-thumb">
                    <div class="news-content">
                        <h3>
							<a href="${pageContext.request.contextPath}/detail?id=${news.id}">
                                <c:out value="${news.title}"/>
                            </a>
                        </h3>
                        <p>
                            <c:choose>
                                <c:when test="${fn:length(news.content) > 150}">
                                    <c:out value="${fn:substring(news.content, 0, 150)}"/>...
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${news.content}"/>
                                </c:otherwise>
                            </c:choose>
                        </p>
                        <span class="news-meta">
                            Đăng ngày: <c:out value="${news.postedDate}"/>
                        </span>
                    </div>
                </article>
            </c:forEach>
        </section>

        <!-- Right Column: Sidebar -->
         <%@ include file="../includes/news_index_left.jsp" %>
</div>

<!-- Footer -->
<%@ include file="../includes/news_index_footer.jsp" %>

</body>
</html>

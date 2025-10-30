<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<%@ page import="ABC_news.Entity.News" %>
<%
    News news = (News) request.getAttribute("news");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="ABC News - Tin tức pháp luật mới nhất tại Việt Nam.">
    <meta name="keywords" content="tin tức, pháp luật, Việt Nam, luật pháp, ABC News">
    <meta name="author" content="ABC News">
    <title>ABC News</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&family=Open+Sans:wght@400;600&display=swap">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
.news-detail-container {
    max-width: 900px;
    margin: 40px auto;
    padding: 20px;
    background: #ffffff;
    border-radius: 12px;
    box-shadow: 0 4px 15px rgba(0,0,0,0.08);
    font-family: 'Open Sans', sans-serif;
    color: #222;
}

.news-detail h1 {
    font-size: 2.2rem;
    font-weight: 700;
    color: #1a1a1a;
    margin-bottom: 10px;
    line-height: 1.4;
}

.news-meta {
    display: block;
    font-size: 0.9rem;
    color: #777;
    margin-bottom: 20px;
    border-bottom: 1px solid #eee;
    padding-bottom: 10px;
}

.news-detail img {
    width: 100%;
    border-radius: 10px;
    margin: 20px 0;
    object-fit: cover;
}

.news-content {
    font-size: 1.05rem;
    line-height: 1.8;
    color: #333;
}

.news-content p {
    margin-bottom: 1.2em;
}

.news-content strong {
    color: #000;
}

.news-content h2, 
.news-content h3 {
    margin-top: 1.8em;
    color: #003366;
    font-weight: 600;
}

.news-content a {
    color: #0066cc;
    text-decoration: none;
}

.news-content a:hover {
    text-decoration: underline;
}

/* Responsive layout */
@media (max-width: 768px) {
    .news-detail-container {
        width: 95%;
        padding: 15px;
    }
    .news-detail h1 {
        font-size: 1.7rem;
    }
    .news-content {
        font-size: 1rem;
    }
}
    </style>
</head>
<body>
	<header class="site-header">
        <div class="container">
            <div class="logo">ABC <span>News</span></div>

<%@ include file="../includes/news_index_nav.jsp" %>



		
		<div class="header-actions">
		    	<form action="${pageContext.request.contextPath}/search" method="get" class="search-form">
				    <input type="text" name="keyword" placeholder="Tìm kiếm tin tức..." class="search-bar" required>
					<button type="submit" class="search-btn">
    					<i class="fa fa-search"></i>
					</button>
					
				</form>
		</div>
		
		
    </header>

<section class="news-detail-container">
    <article class="news-detail">
        <h1>${news.title}</h1>
                        <span class="news-meta">
   						 Đăng ngày: <fmt:formatDate value="${news.publishDate}" pattern="dd/MM/yyyy" /></span>
   						         <img src="${pageContext.request.contextPath}/uploads/${news.image}" alt="${news.title}">
        <div class="news-content">
            ${news.content}
        </div>
    </article>
</section>

<%@ include file="../includes/news_index_footer.jsp" %>
</body>
</html>

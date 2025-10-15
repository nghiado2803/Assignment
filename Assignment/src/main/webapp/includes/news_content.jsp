<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="left-col">
    <h2>Tin ${categoryName}</h2>
    <c:choose>
        <c:when test="${not empty newsList}">
            <c:forEach var="n" items="${newsList}">
                <article class="news-item">
					<img src="${pageContext.request.contextPath}/uploads/${n.image}" alt="${n.title}">
                    <div class="news-content">
                    	<a href="${pageContext.request.contextPath}/detail?id=${n.id}">	
                            ${n.title}
                        </a></h3>
                        <p>${n.summary}</p>
                        <span class="news-meta">Đăng ngày: ${n.publishDate}</span>
                    </div>
                </article>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <p>Chưa có bài viết nào trong thể loại này.</p>
        </c:otherwise>
    </c:choose>
</section>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
           <section class="center-col">
<c:forEach var="n" items="${homeCenter}">
        <article class="news-item">
            <img src="${pageContext.request.contextPath}/uploads/${n.image}" alt="${n.title}">
            <div class="news-content">
                <h3><a href="${pageContext.request.contextPath}/detail?id=${n.id}">${n.title}</a></h3>
                <p>${fn:substring(n.content, 0, 150)}...</p>
                <span class="news-meta">Đăng ngày: ${n.postedDate}</span>
            </div>
        </article>
    </c:forEach>
</section>
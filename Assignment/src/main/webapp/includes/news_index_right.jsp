
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<aside class="right-col">
   <div class="box newsletter">
    <h3>Đăng ký nhận bản tin</h3>
    <p>Nhận tin tức mới nhất từ ABC News qua email của bạn!</p>

    <form id="newsletter-form" 
          action="${pageContext.request.contextPath}/subscribe" 
          method="post" 
          onsubmit="return validateNewsletter()">
          
        <div class="form-group">
            <label for="newsletter-email">Email</label>
            <input type="email" id="newsletter-email" name="email" placeholder="Nhập email của bạn" required>
        </div>

        <button type="submit" class="submit-btn">Đăng ký</button>
    </form>

    <p id="newsletter-error" class="error-message" style="display: none;"></p>
</div>

    <div class="box popular-news">
    <h3>Tin được xem nhiều</h3>
    <ul>
        <c:forEach var="n" items="${topViewed}" varStatus="loop">
            <li>
                <span class="number">${loop.index + 1}</span>
                	<a href="${pageContext.request.contextPath}/detail?id=${n.id}">
                    ${n.title}
                </a>
            </li>
        </c:forEach>
    </ul>
</div>
<div class="box latest-news">
    <h3>Tin mới nhất</h3>
    <ul>
        <c:forEach var="n" items="${latestNews}" varStatus="loop">
            <li>
                <span class="number">${loop.index + 1}</span>
                <a href="${pageContext.request.contextPath}/detail?id=${n.id}">
                    ${n.title}
                </a>
            </li>
        </c:forEach>
    </ul>
</div>
   
</aside>
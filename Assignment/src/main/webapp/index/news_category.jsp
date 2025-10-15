<%@ page contentType="text/html; charset=UTF-8" language="java" %>
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
    body {
        font-family: 'Open Sans', 'Roboto', sans-serif;
        font-size: 16px;
        color: #222;
        line-height: 1.6;
        letter-spacing: 0.3px;
        background-color: #fff;
    }

    h1, h2, h3, h4, h5 {
        font-family: 'Roboto', sans-serif;
        font-weight: 700;
        color: #111;
        letter-spacing: 0.5px;
    }

    a, button {
        font-family: 'Roboto', sans-serif;
        text-decoration: none;
        color: #007bff;
        transition: color 0.2s ease;
    }
    a:hover, button:hover {
        color: #0056b3;
    }

    p {
        font-family: 'Open Sans', sans-serif;
        font-weight: 400;
        color: #333;
        margin-bottom: 1em;
    }

    label, input, button, .form-group {
        font-family: 'Open Sans', sans-serif;
    }
</style>
    
    
</head>
<body>
    <!-- Header -->
    <header class="site-header">
        <div class="container">
            <div class="logo">ABC <span>News</span></div>
            <%@ include file="../includes/news_index_nav.jsp" %>
    </header>

    <div id="login-modal" class="modal">
    <div class="modal-content">
        <span class="close-btn" onclick="closeModal('login-modal')">&times;</span>
        <h2>Đăng nhập</h2>
        <p>Đăng nhập để truy cập tin tức cá nhân hóa và nhiều tính năng hơn.</p>
        <form action="${pageContext.request.contextPath}/login" method="post">
            <input type="hidden" name="redirectURL" value="${pageContext.request.requestURI}">
            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" placeholder="Nhập email của bạn" required>
            </div>
            <div class="form-group">
                <label>Mật khẩu</label>
                <input type="password" name="password" placeholder="Nhập mật khẩu" required>
            </div>
            <button type="submit" class="submit-btn">Đăng nhập</button>
        </form>
    </div>
</div>
    <!-- Main Content -->
    <div class="container">
        <div class="container-2col">
          	<%@ include file="../includes/news_content.jsp" %>
            	

            	<%@ include file="../includes/news_index_right.jsp" %>
        </div>
    </div>

    <!-- Footer -->
<%@ include file="../includes/news_index_footer.jsp" %>

    <script>
        function showModal(modalId) {
            document.getElementById(modalId).style.display = 'flex';
        }

        function closeModal(modalId) {
            document.getElementById(modalId).style.display = 'none';
        }

        function switchModal(currentModalId, targetModalId) {
            closeModal(currentModalId);
            showModal(targetModalId);
        }

        function validateNewsletter() {
            const emailInput = document.getElementById('newsletter-email');
            const errorMessage = document.getElementById('newsletter-error');
            const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

            if (!emailPattern.test(emailInput.value)) {
                errorMessage.textContent = 'Vui lòng nhập email hợp lệ!';
                errorMessage.style.display = 'block';
                return false;
            }

            errorMessage.style.display = 'none';
            alert('Đăng ký nhận bản tin thành công!');
            return true;
        }
    </script>
</body>
</html>

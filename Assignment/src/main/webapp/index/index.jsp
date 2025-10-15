<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="ABC News - Trang tin tức hàng đầu Việt Nam, cập nhật tin tức nhanh chóng, chính xác và đa chiều.">
    <meta name="keywords" content="tin tức, Việt Nam, văn hóa, pháp luật, thể thao, ABC News">
    <meta name="author" content="ABC News">
    <title>ABC News - Trang chủ</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&family=Open+Sans:wght@400;600&display=swap">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    
    <style>
        .toast {
            position: fixed;
            top: 20px;
            left: 50%;
            transform: translateX(-50%);
            background-color: #ff4d4f;
            color: white;
            padding: 12px 25px;
            border-radius: 10px;
            font-weight: bold;
            z-index: 9999;
            box-shadow: 0 2px 8px rgba(0,0,0,0.3);
            animation: fadeInOut 3s ease-in-out;
        }

        @keyframes fadeInOut {
            0% { opacity: 0; transform: translate(-50%, -10px); }
            10%, 90% { opacity: 1; transform: translate(-50%, 0); }
            100% { opacity: 0; transform: translate(-50%, -10px); }
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

    <!-- Login Modal -->
    <div id="login-modal" class="modal">
    <div class="modal-content">
        <span class="close-btn" onclick="closeModal('login-modal')">&times;</span>
        <h2>Đăng nhập</h2>
        <p>Đăng nhập để truy cập tin tức cá nhân hóa và nhiều tính năng hơn.</p>
        <form action="${pageContext.request.contextPath}/login" method="post">
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
        <div class="container-3col">
            <%@ include file="../includes/news_index_left.jsp" %>
            <%@ include file="../includes/news_index_center.jsp" %>
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

        //  Nếu có error=1 trên URL => mở modal đăng nhập + hiển thị thông báo
        document.addEventListener("DOMContentLoaded", function() {
            const params = new URLSearchParams(window.location.search);
            if (params.get("error") === "1") {
                showModal('login-modal');
                const toast = document.createElement("div");
                toast.className = "toast";
                toast.textContent = "Email hoặc mật khẩu không đúng!";
                document.body.appendChild(toast);
                setTimeout(() => toast.remove(), 3000);
            }
        });
    </script>
</body>
</html>

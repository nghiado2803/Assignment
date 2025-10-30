<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ABC News - Trang Quản Trị</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

    <style>
        .popup-overlay {
            position: fixed; inset: 0;
            background: rgba(0,0,0,0.6);
            display: none; justify-content: center; align-items: center;
            z-index: 9999;
        }
        .popup-form {
            position: relative;
            background: #fff;
            width: 380px; padding: 35px 25px 25px;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.3);
            text-align: center;
            animation: fadeIn 0.3s ease-in-out;
        }
        .popup-form h3 { color: #0d47a1; margin-bottom: 8px; }
        .popup-form p { font-size: 14px; color: #555; margin-bottom: 20px; }
        .popup-form input {
            width: 100%; padding: 10px; margin-bottom: 15px;
            border: 1px solid #ccc; border-radius: 8px; font-size: 15px;
        }
        .popup-form button[type="submit"] {
            width: 100%; padding: 10px 25px;
            background: #ffcc00; border: none; border-radius: 8px;
            font-weight: bold; cursor: pointer; transition: 0.3s;
        }
        .popup-form button:hover { background: #fdd835; }
        .close-icon {
            position: absolute; top: 10px; right: 12px;
            background: none; border: none;
            font-size: 22px; font-weight: bold; color: #555;
            cursor: pointer; transition: color 0.2s;
        }
        .close-icon:hover { color: #e53935; }
        @keyframes fadeIn { from {opacity: 0; transform: scale(0.9);} to {opacity: 1; transform: scale(1);} }

        .change-pass-btn {
            display: inline-block;
            background: #ffcc00; color: #000;
            padding: 10px 18px; border-radius: 8px;
            font-weight: bold; text-align: center;
            margin-bottom: 20px; transition: 0.3s;
        }
        .change-pass-btn:hover { background: #fdd835; }

        .admin-banner h2 { margin-bottom: 20px; color: #0d47a1; }
        .admin-stats {
            display: flex; gap: 15px; margin-bottom: 25px;
        }
        .stat-box {
            flex: 1; background: #fff; border-radius: 10px;
            padding: 15px; text-align: center;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        }
        .stat-box h4 { margin: 5px 0; color: #0d47a1; }
        .admin-actions { display: flex; flex-direction: column; gap: 10px; }
        .admin-btn {
            display: block; padding: 10px;
            background: #0d47a1; color: #fff; border-radius: 8px;
            text-align: center; transition: 0.3s;
        }
        .admin-btn:hover { background: #1565c0; }
    </style>
</head>

<body>
    <!-- HEADER -->
    <header class="site-header">
        <div class="container">
            <div class="logo">ABC <span>News</span></div>
         <%@ include file="../includes/news_index_nav.jsp" %>
        </div>
    </header>

    <!-- MAIN -->
    <div class="container">
        <div class="container-3col">

            <!-- Cột trái -->
            <%@ include file="../includes/news_index_left.jsp" %>

            <!-- Cột giữa -->
            <section class="center-col">     
               <h2>Quản trị hệ thống</h2>
                <div class="admin-actions">
    <a href="${pageContext.request.contextPath}/admin/manage_users"
       style="display:inline-block; 
              margin:8px 10px; 
              padding:10px 18px; 
              background:linear-gradient(135deg, #007bff, #0056b3); 
              color:white; 
              font-weight:600; 
              border-radius:8px; 
              text-decoration:none; 
              transition:all 0.25s ease;
              box-shadow:0 2px 5px rgba(0,0,0,0.15);"
       onmouseover="this.style.background='linear-gradient(135deg,#0056b3,#003d80)'"
       onmouseout="this.style.background='linear-gradient(135deg,#007bff,#0056b3)'">
       Quản lý người dùng
    </a>

    <a href="${pageContext.request.contextPath}/admin/manage_categories"
       style="display:inline-block; 
              margin:8px 10px; 
              padding:10px 18px; 
              background:linear-gradient(135deg, #28a745, #1e7e34); 
              color:white; 
              font-weight:600; 
              border-radius:8px; 
              text-decoration:none; 
              transition:all 0.25s ease;
              box-shadow:0 2px 5px rgba(0,0,0,0.15);"
       onmouseover="this.style.background='linear-gradient(135deg,#1e7e34,#155d27)'"
       onmouseout="this.style.background='linear-gradient(135deg,#28a745,#1e7e34)'">
       Quản lý loại tin
    </a>

    <a href="${pageContext.request.contextPath}/admin/manage_all_news"
       style="display:inline-block; 
              margin:8px 10px; 
              padding:10px 18px; 
              background:linear-gradient(135deg, #ffc107, #e0a800); 
              color:#333; 
              font-weight:600; 
              border-radius:8px; 
              text-decoration:none; 
              transition:all 0.25s ease;
              box-shadow:0 2px 5px rgba(0,0,0,0.15);"
       onmouseover="this.style.background='linear-gradient(135deg,#e0a800,#c69500)'"
       onmouseout="this.style.background='linear-gradient(135deg,#ffc107,#e0a800)'">
       Quản lý bài viết
    </a>
    <a href="${pageContext.request.contextPath}/admin/newsletter"
   style="display:inline-block; 
          margin:8px 10px; 
          padding:10px 18px; 
          background:linear-gradient(135deg, #9c27b0, #6d1b7b); 
          color:white; 
          font-weight:600; 
          border-radius:8px; 
          text-decoration:none; 
          transition:all 0.25s ease;
          box-shadow:0 2px 5px rgba(0,0,0,0.15);"
   onmouseover="this.style.background='linear-gradient(135deg,#6d1b7b,#4a148c)'"
   onmouseout="this.style.background='linear-gradient(135deg,#9c27b0,#6d1b7b)'">
   Quản lý email đọc giả
</a>

   
</div>

                
                
            </section>
			
            <aside class="right-col">
                <a href="#" class="change-pass-btn" onclick="openPopup()">Đổi mật khẩu</a>
                <div class="box">
                    <h3>Tin tức hấp dẫn</h3>
                    <ul>
                <%@ include file="../includes/news_index_center.jsp" %>
                </div>
            </aside>
        </div>
    </div>

    <div class="popup-overlay" id="popupOverlay">
        <div class="popup-form">
            <button type="button" class="close-icon" onclick="closePopup()">×</button>
            <h3>Đổi mật khẩu</h3>
            <p>Vui lòng nhập thông tin để thay đổi mật khẩu của bạn</p>

            <form action="${pageContext.request.contextPath}/changePassword" method="post">
                <input type="password" name="oldPassword" placeholder="Mật khẩu cũ" required>
                <input type="password" name="newPassword" placeholder="Mật khẩu mới" required>
                <input type="password" name="confirmPassword" placeholder="Xác nhận mật khẩu mới" required>
                <button type="submit">Xác nhận</button>
            </form>
        </div>
    </div>

<%@ include file="../includes/news_index_footer.jsp" %>

    <script>
        function openPopup() {
            document.getElementById('popupOverlay').style.display = 'flex';
        }
        function closePopup() {
            document.getElementById('popupOverlay').style.display = 'none';
        }
    </script>
</body>
</html>
